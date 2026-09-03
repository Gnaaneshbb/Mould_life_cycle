document.addEventListener("DOMContentLoaded", loadMoulds);

// Set operator automatically from login
document.getElementById("operator").value = localStorage.getItem("username");

// Set today's date by default
document.getElementById("date").value = new Date().toISOString().split("T")[0];

function goToCycles() {

    const date = document.getElementById("date").value;
    const shift = document.getElementById("shift").value;
    const mouldId = document.getElementById("mouldId").value;

    if (!date || !shift || !mouldId) {
        document.getElementById("error").innerText = "All fields are required";
        return;
    }

    // Store values for next page
    localStorage.setItem("date", date);
    localStorage.setItem("shift", shift);
    localStorage.setItem("mouldId", mouldId);

    window.location.href = "mould-dashboard.html?mouldId=" + mouldId;
}

async function loadMoulds(){

const token = localStorage.getItem("token");

const response = await fetch(
"http://localhost:8080/api/moulds/all",
{
headers:{
"Authorization":"Bearer "+token
}
}
);

const moulds = await response.json();

const dropdown = document.getElementById("mouldId");

dropdown.innerHTML = '<option value="">Select Mould ID</option>';

moulds.forEach(mouldId => {

const option = document.createElement("option");

option.value = mouldId;
option.textContent = mouldId;

dropdown.appendChild(option);

});

}

function openDashboard(){

    window.location.href = "report.html";

}
function logout(){

    // clear session
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("mouldId");

    // redirect to login
    window.location.href = "index.html";

}