document.addEventListener("DOMContentLoaded", function () {

    const params = new URLSearchParams(window.location.search);

    const mouldId = params.get("mouldId");

    console.log("MouldId from URL:", mouldId);

    if (!mouldId) {
        alert("Mould ID missing");
        window.location.href = "supervisor.html";
        return;
    }

    document.getElementById("mouldId").innerText = mouldId;

});


// =======================================
// ACCEPT / REJECT INSPECTION
// =======================================
async function submitInspection(result) {

    const token = localStorage.getItem("token");

    const params = new URLSearchParams(window.location.search);
    const mouldId = params.get("mouldId");

    const fileInput = document.getElementById("inspectionFile");

    if (!fileInput.files.length) {
        alert("Please select a PDF file");
        return;
    }

    const file = fileInput.files[0];

    if (file.type !== "application/pdf") {
        alert("Only PDF files allowed");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("result", result);   // accepted / rejected

    try {

        const response = await fetch(
            `http://localhost:8080/api/moulds/${mouldId}/inspection-complete`,
            {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                },
                body: formData
            }
        );

        const text = await response.text();

        console.log("Status:", response.status);
        console.log("Response:", text);

        if (!response.ok) {
            alert("Server Error: " + text);
            return;
        }

        alert(text || "Inspection processed successfully");

        // redirect back to supervisor dashboard
        window.location.href = "supervisor.html";

    } catch (error) {

        console.error(error);
        alert("Upload failed. Check console.");

    }
}


// =======================================
// BACK BUTTON
// =======================================
function goBack(){

    window.location.href="supervisor.html";

}