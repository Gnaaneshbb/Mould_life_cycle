const BASE_URL = "http://localhost:8080/api";
const token = localStorage.getItem("token");


/* ================================
   ADD MOULD
================================ */

async function addMould(){

const mouldId = document.getElementById("mouldId").value;
const mouldNumber = document.getElementById("mouldNumber").value;
const outerDia = document.getElementById("outerDia").value;
const innerDia = document.getElementById("innerDia").value;
const type = document.getElementById("type").value;

const response = await fetch(BASE_URL + "/admin/mould",{

method:"POST",

headers:{
"Content-Type":"application/json",
"Authorization":"Bearer "+token
},

body:JSON.stringify({
mouldId:mouldId,
mouldNumber:mouldNumber,
outerDia:parseInt(outerDia),
innerDia:parseInt(innerDia),
type:type
})

});

if(response.ok){

alert("Mould added successfully");

/* Clear input fields */

document.getElementById("mouldId").value="";
document.getElementById("mouldNumber").value="";
document.getElementById("outerDia").value="";
document.getElementById("innerDia").value="";
document.getElementById("type").value="";

}else{

alert("Failed to add mould");

}

}



/* ================================
   CREATE USER
================================ */

async function createUser(){

const username=document.getElementById("username").value;
const password=document.getElementById("password").value;
const role=document.getElementById("role").value;
const email=document.getElementById("email").value;

const response=await fetch(BASE_URL+"/admin/user",{

method:"POST",

headers:{
"Content-Type":"application/json",
"Authorization":"Bearer "+token
},

body:JSON.stringify({
username:username,
password:password,
role:role,
email:email
})

});

if(response.ok){

alert("User created successfully");

document.getElementById("username").value="";
document.getElementById("password").value="";
document.getElementById("email").value="";
document.getElementById("role").value="ROLE_OPERATOR";

}else{

alert("Failed to create user");

}

}



/* ================================
   LOAD PENDING INSPECTIONS
================================ */

async function loadPending(){

const response=await fetch(BASE_URL+"/admin/inspection-pending",{

headers:{
"Authorization":"Bearer "+token
}

});

const data=await response.json();

const table=document.querySelector("#pendingTable tbody");

table.innerHTML="";

data.forEach(mould=>{

const row=document.createElement("tr");

row.innerHTML=`
<td>${mould.mouldId}</td>
<td>${mould.status}</td>
`;

table.appendChild(row);

});

}



/* ================================
   LOAD COMPLETED INSPECTIONS
================================ */

async function loadCompleted(){

const response=await fetch(BASE_URL+"/admin/inspection-completed",{

headers:{
"Authorization":"Bearer "+token
}

});

const data=await response.json();

const table=document.querySelector("#completedTable tbody");

table.innerHTML="";

data.forEach(mould=>{

const row=document.createElement("tr");

row.innerHTML=`
<td>${mould.mouldId}</td>
<td>${mould.status}</td>
`;

table.appendChild(row);

});

}



/* ================================
   OPEN DASHBOARD REPORT
================================ */

function openDashboard(){

window.location.href = "report.html";

}



/* ================================
   LOGOUT
================================ */

function logout(){

localStorage.removeItem("token");
localStorage.removeItem("username");
localStorage.removeItem("mouldId");

window.location.href = "index.html";

}



async function deleteMould(){

const mouldId = document.getElementById("deleteMouldId").value;

const response = await fetch(
BASE_URL + "/moulds/" + mouldId,
{
method:"DELETE",
headers:{
"Authorization":"Bearer "+token
}
});

if(response.ok){

alert("Mould deleted successfully");

document.getElementById("deleteMouldId").value="";

}else{

alert("Failed to delete mould");

}

}