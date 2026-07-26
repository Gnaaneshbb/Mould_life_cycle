package com.example.controller;

import com.example.dto.MouldDashboardResponse;
import com.example.dto.MouldDetailsResponse;
import com.example.dto.MouldHistoryResponse;
import com.example.dto.ShiftCycleRequest;
import com.example.entity.Mould;
import com.example.service.MouldService;
import com.example.service.InspectionService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/moulds")
public class MouldController {

    private final MouldService mouldService;
    private final InspectionService inspectionService;

    public MouldController(
            MouldService mouldService,
            InspectionService inspectionService
    ) {
        this.mouldService = mouldService;
        this.inspectionService = inspectionService;
    }

    // Register mould
    @PostMapping("/register")
    public Mould register(@RequestParam String mouldId) {
        return mouldService.registerMould(mouldId);
    }

    // Shift cycle entry
    @PostMapping("/shift")
    public Mould addShift(@RequestBody ShiftCycleRequest request) {
        return mouldService.addShiftCycles(request);
    }

    // Get mould details
    @GetMapping("/{mouldId}/details")
    public MouldDetailsResponse getDetails(@PathVariable String mouldId) {
        return mouldService.getMouldDetails(mouldId);
    }

    // Get mould history
    @GetMapping("/{mouldId}/history")
    public MouldHistoryResponse getHistory(@PathVariable String mouldId) {
        return mouldService.getMouldHistory(mouldId);
    }

    // Get moulds pending inspection
    @GetMapping("/inspection-pending")
    public List<MouldDetailsResponse> getInspectionPending() {
        return mouldService.getInspectionPending();
    }

    // Complete inspection and upload report
    @PostMapping("/{mouldId}/inspection-complete")
    public ResponseEntity<String> completeInspection(

            @PathVariable String mouldId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("result") String result
    ) {

        try {

            // save inspection pdf
            inspectionService.saveInspection(mouldId, file);

            if (result.equalsIgnoreCase("accepted")) {

                mouldService.completeInspection(mouldId);

                return ResponseEntity.ok("Inspection Accepted. Mould Activated.");

            } else {

                mouldService.blockMould(mouldId);

                return ResponseEntity.ok("Inspection Rejected. Mould Blocked.");
            }

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Inspection failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/dashboard-range")
    public List<Mould> getMouldsInRange(
            @RequestParam int min,
            @RequestParam int max
    ){
        return mouldService.getMouldsInRange(min, max);
    }
    
    
    @GetMapping("/{mouldId}/dashboard")
    public MouldDashboardResponse getDashboard(
            @PathVariable String mouldId) {

        return mouldService.getMouldDashboard(mouldId);
    }
    
    
    @GetMapping("/report")
    public Map<String, Long> getReport(){

        return mouldService.getCycleRanges();

    }
    
    
    @DeleteMapping("/{mouldId}")
    public String deleteMould(@PathVariable String mouldId){

        mouldService.deleteMould(mouldId);

        return "Mould deleted";

    }

    
    
    // NEW API: Get all mould IDs for dropdown
    @GetMapping("/all")
    public List<String> getAllMouldIds() {
        return mouldService.getAllMouldIds();
    }

}