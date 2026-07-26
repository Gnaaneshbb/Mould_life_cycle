package com.example.service;

import com.example.dto.*;
import com.example.entity.DeletedMould;
import com.example.entity.Mould;
import com.example.entity.ShiftEntry;
import com.example.enums.MouldStatus;
import com.example.repository.DeletedMouldRepository;
import com.example.repository.MouldRepository;
import com.example.repository.ShiftEntryRepository;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MouldService {

    private static final int INSPECTION_WARNING = 7500;
    private static final int INSPECTION_INTERVAL = 8000;

    private final MouldRepository mouldRepository;
    private final ShiftEntryRepository shiftEntryRepository;
    private final EmailService emailService;
    private final DeletedMouldRepository deletedMouldRepository;

    public MouldService(
            MouldRepository mouldRepository,
            ShiftEntryRepository shiftEntryRepository,
            EmailService emailService,
            DeletedMouldRepository deletedMouldRepository) {

        this.mouldRepository = mouldRepository;
        this.shiftEntryRepository = shiftEntryRepository;
        this.emailService = emailService;
        this.deletedMouldRepository = deletedMouldRepository;
    }

    // Register mould
    public Mould registerMould(String mouldId) {

        if (mouldRepository.existsById(mouldId)) {
            throw new RuntimeException("Mould already exists");
        }

        return mouldRepository.save(new Mould(mouldId));
    }

    // Shift-wise cycle entry
    public Mould addShiftCycles(ShiftCycleRequest request) {

        Mould mould = mouldRepository.findById(request.getMouldId())
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        if (mould.getStatus() != MouldStatus.ACTIVE) {
            throw new RuntimeException("Mould not allowed for production");
        }

        int previous = mould.getTotalCycles();
        int current = previous + request.getEnteredCycles();

        mould.setTotalCycles(current);

        if (previous < mould.getNextInspectionAt()
                && current >= mould.getNextInspectionAt()) {

            mould.setStatus(MouldStatus.INSPECTION_PENDING);

            mould.setNextInspectionAt(
                    mould.getNextInspectionAt() + INSPECTION_INTERVAL
            );
        }

        return mouldRepository.save(mould);
    }

    // ML result handling
    public Mould handleMLResult(MLResultRequest request) {

        Mould mould = mouldRepository.findById(request.getMouldId())
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        if ("APPROVED".equalsIgnoreCase(request.getResult())) {

            mould.setStatus(MouldStatus.QR_VALID);

        } else {

            mould.setStatus(MouldStatus.QR_INVALID);
            mould.setStatus(MouldStatus.BLOCKED);

        }

        return mouldRepository.save(mould);
    }

    // ==========================================
    // PRODUCTION ENTRY
    // ==========================================
    public DashboardResponse submitProduction(
            String operatorUsername,
            ProductionRequest request) {

        Mould mould = mouldRepository.findById(request.getMouldId())
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        System.out.println("STATUS FROM ENTITY: " + mould.getStatus());

        if (mould.getStatus() == MouldStatus.INSPECTION_PENDING) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Inspection Pending. Upload inspection report."
            );
        }

        if (mould.getStatus() == MouldStatus.BLOCKED) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Mould Blocked. Contact Supervisor."
            );
        }

        int previous = mould.getTotalCycles();
        int current = previous + request.getEnteredCycles();

        mould.setTotalCycles(current);

        // =====================================
        // WARNING MAIL AFTER 7500 CYCLES
        // =====================================
        if (current >= INSPECTION_WARNING && current < mould.getNextInspectionAt()) {

            emailService.sendInspectionAlert(
                    mould.getMouldId(),
                    current
            );
        }

        // =====================================
        // FINAL INSPECTION AT 8000
        // =====================================
        if (previous < mould.getNextInspectionAt()
                && current >= mould.getNextInspectionAt()) {

            mould.setStatus(MouldStatus.INSPECTION_PENDING);

            emailService.sendInspectionAlert(
                    mould.getMouldId(),
                    current
            );

            mould.setNextInspectionAt(
                    mould.getNextInspectionAt() + INSPECTION_INTERVAL
            );
        }

        mouldRepository.save(mould);

        // Save shift entry
        ShiftEntry entry = new ShiftEntry();

        entry.setOperatorUsername(operatorUsername);
        entry.setMouldId(request.getMouldId());
        entry.setDate(request.getDate());
        entry.setShift(request.getShift());
        entry.setEnteredCycles(request.getEnteredCycles());
        entry.setTimestamp(java.time.LocalDateTime.now());

        shiftEntryRepository.save(entry);

        return new DashboardResponse(
                mould.getMouldId(),
                mould.getTotalCycles(),
                mould.getStatus().name(),
                mould.getNextInspectionAt()
        );
    }

    public MouldDetailsResponse getMouldDetails(String mouldId) {

        Mould mould = mouldRepository.findById(mouldId)
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        return new MouldDetailsResponse(
                mould.getMouldId(),
                mould.getTotalCycles(),
                mould.getStatus(),
                mould.getNextInspectionAt()
        );
    }

    public MouldHistoryResponse getMouldHistory(String mouldId) {

        Mould mould = mouldRepository.findById(mouldId)
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        List<ShiftEntryDTO> history = shiftEntryRepository
                .findByMouldIdOrderByTimestampDesc(mouldId)
                .stream()
                .map(entry -> new ShiftEntryDTO(
                        entry.getDate(),
                        entry.getShift(),
                        entry.getOperatorUsername(),
                        entry.getEnteredCycles()
                ))
                .toList();

        return new MouldHistoryResponse(
                mould.getMouldId(),
                mould.getTotalCycles(),
                mould.getStatus(),
                history
        );
    }

    public ShiftSummaryResponse getShiftSummary(
            LocalDate date,
            String shift) {

        List<ShiftEntry> entries =
                shiftEntryRepository
                        .findByDateAndShiftOrderByTimestampAsc(date, shift);

        int totalCycles = entries.stream()
                .mapToInt(ShiftEntry::getEnteredCycles)
                .sum();

        List<ShiftMouldEntry> mouldEntries = entries.stream()
                .map(e -> new ShiftMouldEntry(
                        e.getMouldId(),
                        e.getEnteredCycles()
                ))
                .toList();

        return new ShiftSummaryResponse(
                date,
                shift,
                totalCycles,
                entries.size(),
                mouldEntries
        );
    }

    public List<MouldDetailsResponse> getInspectionPending() {

        return mouldRepository
                .findByStatus(MouldStatus.INSPECTION_PENDING)
                .stream()
                .map(m -> new MouldDetailsResponse(
                        m.getMouldId(),
                        m.getTotalCycles(),
                        m.getStatus(),
                        m.getNextInspectionAt()
                ))
                .toList();
    }

    public void completeInspection(String mouldId) {

        Mould mould = mouldRepository.findById(mouldId)
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        mould.setStatus(MouldStatus.ACTIVE);

        mouldRepository.save(mould);
    }

    public Mould createMould(Mould mould){

        mould.setStatus(MouldStatus.ACTIVE);
        mould.setTotalCycles(0);
        mould.setNextInspectionAt(8000);

        // 🔹 Automatically generate size
        String size = mould.getOuterDia() + "X" + mould.getInnerDia();

        mould.setMouldSize(size);

        return mouldRepository.save(mould);
    }

    public MouldDashboardResponse getMouldDashboard(String mouldId) {

        Mould mould = mouldRepository.findById(mouldId)
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        return new MouldDashboardResponse(
                mould.getMouldId(),
                mould.getTotalCycles(),
                mould.getStatus(),
                mould.getNextInspectionAt()
        );
    }
    
    
    public void blockMould(String mouldId) {

        Mould mould = mouldRepository.findById(mouldId)
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        mould.setStatus(MouldStatus.BLOCKED);

        mouldRepository.save(mould);
    }
    
    
    
    public List<Mould> getMouldsInRange(int min, int max){

        return mouldRepository.findAll()
                .stream()
                .filter(m -> m.getTotalCycles() >= min &&
                             m.getTotalCycles() <= max)
                .toList();
    }

    // ===============================
    // DROPDOWN MOULD LIST
    // ===============================
    public List<String> getAllMouldIds() {

        return mouldRepository
                .findAll()
                .stream()
                .map(Mould::getMouldId)
                .toList();
    }
    
    
    public Map<String, Long> getCycleRanges() {

        Map<String, Long> ranges = new LinkedHashMap<>();

        ranges.put("7000-8000",
                mouldRepository.countByTotalCyclesBetween(7000,8000));

        ranges.put("15000-16000",
                mouldRepository.countByTotalCyclesBetween(15000,16000));

        ranges.put("23000-24000",
                mouldRepository.countByTotalCyclesBetween(23000,24000));

        ranges.put("31000-32000",
                mouldRepository.countByTotalCyclesBetween(31000,32000));

        ranges.put("39000-40000",
                mouldRepository.countByTotalCyclesBetween(39000,40000));

        ranges.put("47000-48000",
                mouldRepository.countByTotalCyclesBetween(47000,48000));

        ranges.put("51000-52000",
                mouldRepository.countByTotalCyclesBetween(51000,52000));

        return ranges;
    }
    
    
    public void deleteMould(String mouldId){

        Mould mould = mouldRepository.findById(mouldId)
                .orElseThrow(() -> new RuntimeException("Mould not found"));

        DeletedMould deleted = new DeletedMould();

        deleted.setMouldId(mould.getMouldId());
        deleted.setMouldNumber(mould.getMouldNumber());
        deleted.setMouldSize(mould.getMouldSize());
        deleted.setOuterDia(mould.getOuterDia());
        deleted.setInnerDia(mould.getInnerDia());
        deleted.setType(mould.getType());
        deleted.setTotalCycles(mould.getTotalCycles());

        deletedMouldRepository.save(deleted);

        mouldRepository.delete(mould);
    }

}