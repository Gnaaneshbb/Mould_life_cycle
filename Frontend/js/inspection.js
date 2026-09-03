document.addEventListener("DOMContentLoaded", function () {

    const token = localStorage.getItem("token");

    if (!token) {
        alert("Session expired. Please login again.");
        window.location.href = "index.html";
        return;
    }

    const mouldId = localStorage.getItem("mouldId");
    const mouldDisplay = document.getElementById("mouldDisplay");
    const form = document.getElementById("inspectionForm");
    const fileInput = document.getElementById("inspectionFile");
    const statusMessage = document.getElementById("statusMessage");
    const backBtn = document.getElementById("backBtn");

    if (!mouldId) {
        alert("No mould selected.");
        window.location.href = "cycles.html";
        return;
    }

    mouldDisplay.textContent = mouldId;

    // ==============================
    // Upload Inspection
    // ==============================
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        if (!fileInput || fileInput.files.length === 0) {
            alert("Please select a PDF file.");
            return;
        }

        const file = fileInput.files[0];

        if (file.type !== "application/pdf") {
            alert("Only PDF files are allowed.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {

            const response = await fetch(
                `http://localhost:8080/api/moulds/${mouldId}/upload-inspection`,
                {
                    method: "POST",
                    headers: {
                        "Authorization": "Bearer " + token
                    },
                    body: formData
                }
            );

            console.log("Upload status:", response.status);

            const message = await response.text();

            if (!response.ok) {
                alert("Upload failed: " + message);
                return;
            }

            statusMessage.innerHTML =
                `<p style="color:green;">${message}</p>`;

            // Redirect back to production after success
            setTimeout(() => {
                window.location.href = "cycles.html";
            }, 1500);

        } catch (error) {
            console.error("Upload error:", error);
            alert("Server error while uploading.");
        }
    });

    // ==============================
    // Back Button
    // ==============================
    backBtn.addEventListener("click", function () {
        window.location.href = "cycles.html";
    });

});