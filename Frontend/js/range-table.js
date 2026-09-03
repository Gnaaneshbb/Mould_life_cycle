const params = new URLSearchParams(window.location.search);

const min = params.get("min");
const max = params.get("max");

const token = localStorage.getItem("token");

loadMoulds();

async function loadMoulds(){

const response = await fetch(
`http://localhost:8080/api/moulds/dashboard-range?min=${min}&max=${max}`,
{
headers:{
"Authorization":"Bearer "+token
}
});

const moulds = await response.json();

const tbody = document.getElementById("tableBody");

tbody.innerHTML="";

moulds.forEach(m=>{

const row = document.createElement("tr");

row.innerHTML=`
<td>${m.mouldId}</td>
<td>${m.mouldSize}</td>
<td>${m.mouldNumber}</td>
<td>${m.totalCycles}</td>
<td>${m.nextInspectionAt}</td>
`;

tbody.appendChild(row);

});

}