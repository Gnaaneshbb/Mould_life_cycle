document.addEventListener("DOMContentLoaded", async function () {

    const token = localStorage.getItem("token");
    const mouldId = localStorage.getItem("mouldId");

    if (!token) {
        window.location.href = "index.html";
        return;
    }

    try {

        const response = await fetch(
            `http://localhost:8080/api/moulds/${mouldId}/details`,
            {
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );

        const data = await response.json();

        document.getElementById("mouldIdDisplay").innerText = data.mouldId;
        document.getElementById("totalCycles").innerText = data.totalCycles;
        document.getElementById("nextInspection").innerText = data.nextInspectionAt;

        const badge = document.getElementById("statusBadge");
        badge.innerText = data.status;

        // Remove old classes
        badge.classList.remove(
            "status-active",
            "status-inspection",
            "status-blocked"
        );

        if (data.status === "ACTIVE") {
            badge.classList.add("status-active");
        } else if (data.status === "INSPECTION_PENDING") {
            badge.classList.add("status-inspection");
        } else {
            badge.classList.add("status-blocked");
        }

    } catch (error) {
        alert("Error loading dashboard");
    }

});

function goBack(){

    window.location.href = "header.html";

}