document.addEventListener("DOMContentLoaded", function () {

    const token = localStorage.getItem("token");

    // If not logged in → redirect
    if (!token) {
        window.location.href = "index.html";
        return;
    }

    const mouldInput = document.getElementById("mouldId");
    const cyclesInput = document.getElementById("cycles");
    const form = document.getElementById("cycleForm");

    const savedMould = localStorage.getItem("mouldId");

    if (savedMould && mouldInput) {
        mouldInput.value = savedMould;
        mouldInput.readOnly = true;
    }

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const mouldId = mouldInput.value;
        const enteredCycles = parseInt(cyclesInput.value);

        if (!enteredCycles || enteredCycles <= 0) {
            alert("Enter valid cycle count");
            return;
        }

        try {

            const response = await fetch("http://localhost:8080/api/production/submit", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify({
                    mouldId: mouldId,
                    enteredCycles: enteredCycles
                })
            });

            console.log("HTTP Status:", response.status);

            // ===============================
            // HANDLE ERROR RESPONSES
            // ===============================
            if (!response.ok) {

            console.log("HTTP Status:", response.status);

            let errorText = "";

            try {
                errorText = await response.text();
            } catch (e) {
                console.log("No error body");
            }

            console.log("Raw error text:", errorText);

            if (response.status === 409) {
                alert("Inspection pending. Please upload inspection report.");
                window.location.href = "inspection.html";
                return;
            }

            if (response.status === 403) {
                alert("Mould blocked. Contact supervisor.");
                return;
            }

            if (response.status === 401) {
                alert("Session expired. Please login again.");
                localStorage.clear();
                window.location.href = "index.html";
                return;
            }

            alert("Unexpected error: " + response.status);
            return;
        }

            // ===============================
            // SUCCESS RESPONSE
            // ===============================
            const data = await response.json();

            console.log("Success response:", data);

            localStorage.setItem("mouldId", mouldId);

            window.location.href = "dashboard.html";

        } catch (error) {
            console.error("Fetch error:", error);
            alert("Server error. Please try again.");
        }
    });

});