async function login() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch(BASE_URL + "/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    if (response.ok) {

        const data = await response.json();
        const token = data.token;

        // store token
        localStorage.setItem("token", token);
        localStorage.setItem("username", username);

        // decode token
        const payload = JSON.parse(atob(token.split('.')[1]));
        const role = payload.role;

        console.log("Logged in role:", role);

        // role based redirect
        if (role === "ROLE_ADMIN") {

            window.location.href = "admin.html";

        } else if (role === "ROLE_SUPERVISOR") {

            window.location.href = "supervisor.html";

        } else if (role === "ROLE_OPERATOR") {

            window.location.href = "header.html";

        } else {

            document.getElementById("error").innerText = "Unknown role";

        }

    } else {

        document.getElementById("error").innerText = "Invalid credentials";

    }
}