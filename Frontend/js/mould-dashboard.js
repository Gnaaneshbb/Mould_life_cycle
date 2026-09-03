document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard(){

const token = localStorage.getItem("token");

const params = new URLSearchParams(window.location.search);
const mouldId = params.get("mouldId");

if(!mouldId){
alert("Mould not selected");
window.location.href="header.html";
return;
}

document.getElementById("mouldId").innerText = mouldId;

try{

const response = await fetch(
`http://localhost:8080/api/moulds/${mouldId}/dashboard`,
{
headers:{
"Authorization":"Bearer "+token
}
}
);

const data = await response.json();

document.getElementById("totalCycles").innerText = data.totalCycles;
document.getElementById("status").innerText = data.status;
document.getElementById("nextInspection").innerText = data.nextInspectionAt;

}catch(error){

console.error(error);
alert("Failed to load mould dashboard");

}

}

function addCycles(){

const params = new URLSearchParams(window.location.search);
const mouldId = params.get("mouldId");

window.location.href = "cycles.html?mouldId="+mouldId;

}