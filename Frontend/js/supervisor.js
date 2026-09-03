document.addEventListener("DOMContentLoaded", loadMoulds);

const token = localStorage.getItem("token");

async function loadMoulds() {

    const tbody = document.querySelector("#mouldTable tbody");

    tbody.innerHTML = "<tr><td colspan='2'>Loading...</td></tr>";

    try {

        const response = await fetch(
            "http://localhost:8080/api/moulds/inspection-pending",
            {
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );

        const moulds = await response.json();

        tbody.innerHTML = "";

        if (moulds.length === 0) {

            tbody.innerHTML =
                "<tr><td colspan='2'>No moulds pending inspection</td></tr>";

            return;
        }

        moulds.forEach(mould => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${mould.mouldId}</td>
                <td>${mould.status}</td>
            `;

            // Click row → open inspection page
            row.onclick = () => {

                window.location.href =
                    "inspection-upload.html?mouldId=" + mould.mouldId;

            };

            tbody.appendChild(row);

        });

    } catch (error) {

        console.error(error);

        tbody.innerHTML =
            "<tr><td colspan='2'>Failed to load moulds</td></tr>";

    }

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