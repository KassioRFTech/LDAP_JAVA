var city = document.querySelector(".infoPas");
var arrow = document.querySelector(".seta");
var cidade = document.querySelectorAll(".cidades");

var pa = document.querySelector(".cidade-container");
var userContainer = document.querySelector(".users-container");

var usersIntPrinc = document.querySelectorAll(".users-int-principal");
var userTop = document.querySelector(".users-top");
var userMid = document.querySelector(".users-mid");
var usersIntTop = document.querySelectorAll(".users-int-top");
var usersIntMid = document.querySelectorAll(".users-int-mid");
var usersIntBottom = document.querySelectorAll(".users-int-bottom");

//const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
//const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");


city.addEventListener("click", ()=>{

    if(arrow.style.transform == "rotate(180deg)"){

        cidade = document.querySelectorAll(".cidades");
        arrow.style.transform = "rotate(0deg)";
        arrow.style.transition = "0.5s";

        cidade.forEach((e)=>{
            e.remove();
        });

    }else{

        arrow.style.transform = "rotate(180deg)";
        arrow.style.transition = "0.5s";

        fetch("http://10.11.71.30:8080/Ous")
        .then((resp) => {
    
            return resp.json();
    
        }).then((data) =>{
            data.forEach((item) => {

                var p = document.createElement("p");
                p.classList.add("cidades");
                p.textContent = item.servicePoint;
                pa.appendChild(p);

                    p.addEventListener("click", ()=>{
                        fetch("http://10.11.71.30:8080/users/" + p.textContent)
                        .then((resp) => {
                            return resp.json();
                        }).then((data)=>{

                            if(!userContainer){

                                userContainer = document.createElement("div");
                                userContainer.classList.add("users-container");
                                document.body.appendChild(userContainer);

                                userTop = document.createElement("div");
                                userTop.classList.add("users-top");
                                userContainer.appendChild(userTop);
                                userTop.textContent = "Usuarios - " + p.textContent;

                                userMid= document.createElement("div");
                                userMid.classList.add("users-mid");
                                userContainer.appendChild(userMid);

                            }else{

                                userContainer.remove();
                                userContainer.innerHTML = "";
                                userTop.remove();
                                userMid.remove();

                                userContainer = document.createElement("div");
                                userContainer.classList.add("users-container");
                                document.body.appendChild(userContainer);

                                userTop = document.createElement("div");
                                userTop.classList.add("users-top");
                                userContainer.appendChild(userTop);
                                userTop.textContent = "Usuarios - " + p.textContent;

                                userMid= document.createElement("div");
                                userMid.classList.add("users-mid");
                                userContainer.appendChild(userMid);
    
                            }

                            userMid.addEventListener("click", (event) => {

                                const lock = event.target.closest(".lock");
                                const unlock = event.target.closest(".unlock");
                                const passwords = event.target.closest(".password");

                                if (lock) {
                                    const motiv = prompt("Qual o motivo do bloqueio ?");
                                                
                                    if (motiv === null) {
                                        alert("Operação cancelada");
                                        return;
                                    }

                                    const name = lock.dataset.name;

                                    fetch("http://10.11.71.30:8080/users/" + name,{
                                        method: "PATCH",

                                        headers:{
                                            "Content-Type": "application/json",
											//[csrfHeader]: csrfToken
                                        },

                                        body: JSON.stringify({
                                            description: motiv,
                                            accountControl: "desativar"
                                        })
                                    }).then((resp)=>{
                                        return resp.json();
                                    }).then((data)=>{
                                        alert("Usuario: " + JSON.stringify(data.cn) + " foi desativado com sucesso");
                                    }).catch((error)=>{
                                        alert(error)
                                    });

                                }else if (unlock){
                                    const name = unlock.dataset.name;

                                    fetch("http://10.11.71.30:8080/users/" + name,{
                                        method: "PATCH",

                                        headers:{
                                            "Content-Type": "application/json",
											//[csrfHeader]: csrfToken
                                        },

                                        body: JSON.stringify({
                                            accountControl: "ativar"
                                        })
                                    }).then((resp)=>{
                                        return resp.json();
                                    }).then((data)=>{
                                        alert("Usuario: " + JSON.stringify(data.cn) + " foi ativado com sucesso");
                                    }).catch((error)=>{
                                        alert(error)
                                    });

                                }else if(passwords){

                                    const changepass = prompt("Digite a nova senha");
                                    
                                    if (changepass === null) {
                                        alert("Operação cancelada");
                                        return;
                                    }
                                    
                                    const name = passwords.dataset.name;

                                    fetch("http://10.11.71.30:8080/users/" + name,{

                                        method: "PATCH",

                                        headers:{
                                            "Content-Type": "application/json",
											//[csrfHeader]: csrfToken
                                        },

                                        body: JSON.stringify({
                                            pass: changepass
                                        })
                                    
                                    }).then((resp) =>{
                                        return resp.json();
                                    }).then((data)=>{
                                        alert("A senha do usuario " + JSON.stringify(data.cn) + " foi redefinida com sucesso");
                                    }).catch((error) =>{
                                        alert(error);
                                    });
                                    
                                }

                            });

                            data.forEach((e)=>{

                                usersIntPrinc = document.createElement("div");
                                usersIntPrinc.classList.add("users-int-principal");
                                userMid.appendChild(usersIntPrinc);

                                usersIntTop = document.createElement("div");
                                usersIntTop.classList.add("users-int-top");
                                usersIntPrinc.appendChild(usersIntTop);

                                usersIntTop.textContent = e.cn;

                                usersIntMid = document.createElement("div");
                                usersIntMid.classList.add("users-int-mid");
                                usersIntPrinc.appendChild(usersIntMid);

                                usersIntBottom = document.createElement("div");
                                usersIntBottom.classList.add("users-int-bottom");
                                usersIntPrinc.appendChild(usersIntBottom);

                                if(e.description == "null" || e.description == " "){
                                    var grupo = [];
                                    var grupo2 = [];
                                    var grupos;
                                    grupo =  e.memberOf.split(",");
    
                                    var login =
                                    "Login do usuario: " + e.account + "<br><br>";
                                    
                                    var sta = "Status: usuario ativo" + "<br><br>Grupos do usuario" + "<svg class='seta-grupo' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 512 512'><path d='M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z'/></svg>";
    
                                    for(i = 0; i < grupo.length;i++){
                                        grupo2[i] = grupo[i]+ "<br>";
                                    }
    
                                    grupos = "<div class='grupo'>" + grupo2.join("") + "</div>";
                                    var usersGrupo = document.querySelectorAll(".grupo")
    
                                        usersGrupo.forEach((a)=>{
                                            if(a.style.width < "120px"){
                                                a.style.overflowY = "visible";
                                            }else{
                                                a.style.overflowY = "scroll";
                                            }
                                    });

                                    var figures =
                                    "<svg data-name='"+ e.account +"' class='lock' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 448 512'>" + 
                                    "<title>Bloquear usuario</title>" +
                                    "<path d='M144 144l0 48 160 0 0-48c0-44.2-35.8-80-80-80s-80 35.8-80 80zM80 192l0-48C80 64.5 144.5 0 224 0s144 64.5 144 144l0 48 16 0c35.3 0 64 28.7 64 64l0 192c0 35.3-28.7 64-64 64L64 512c-35.3 0-64-28.7-64-64L0 256c0-35.3 28.7-64 64-64l16 0z'/></svg>" + 
                                    "<svg data-name='"+ e.account +"' class='password' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 512 512'>" + 
                                    "<title>Trocar senha</title>" +
                                    "<path d='M336 352c97.2 0 176-78.8 176-176S433.2 0 336 0S160 78.8 160 176c0 18.7 2.9 36.8 8.3 53.7L7 391c-4.5 4.5-7 10.6-7 17l0 80c0 13.3 10.7 24 24 24l80 0c13.3 0 24-10.7 24-24l0-40 40 0c13.3 0 24-10.7 24-24l0-40 40 0c6.4 0 12.5-2.5 17-7l33.3-33.3c16.9 5.4 35 8.3 53.7 8.3zM376 96a40 40 0 1 1 0 80 40 40 0 1 1 0-80z'/></svg>";

                                    usersIntBottom.innerHTML = figures;
                                    usersIntMid.innerHTML = login + sta + grupos;


                                    const grupoDiv = usersIntMid.querySelector(".grupo");
                                    const seta = usersIntMid.querySelector(".seta-grupo");

                                    seta.addEventListener("click", () => {
                                        const isVisible = grupoDiv.style.display === "block";
                                        grupoDiv.style.display = isVisible ? "none" : "block";
                                        seta.style.transform = isVisible ? "rotate(0deg)" : "rotate(180deg)";
                                        seta.style.transition = "0.5s";
                                    });
    
                               }else{
                                var grupo = [];
                                var grupo2 = [];
                                var grupos;
                                grupo =  e.memberOf.split(",");

                                var login =
                                "Login do usuario: " + e.account + "<br><br>";
                                
                                var sta = "Status: usuario desativo" + "<br>";
                                var status = "Motivo: " + e.description + "<br><br>Grupos do usuario" + "<svg class='seta-grupo' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 512 512'><path d='M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z'/></svg>";

                                for(i = 0; i < grupo.length;i++){
                                    grupo2[i] = grupo[i]+ "<br>";
                                }

                                usersIntTop.style.backgroundColor = "red";

                                grupos = "<div class='grupo'>" + grupo2.join("") + "</div>";
                                var usersGrupo = document.querySelectorAll(".grupo");

                                    usersGrupo.forEach((a)=>{
                                        if(a.style.width < "120px"){
                                            a.style.overflowY = "visible";
                                        }else{
                                            a.style.overflowY = "scroll";
                                        }
                                });

                                var figures =
                                "<svg data-name='"+ e.account +"' class='unlock' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 576 512'>" +
                                "<title>Desbloquear usuario</title>" +
                                "<path d='M352 144c0-44.2 35.8-80 80-80s80 35.8 80 80l0 48c0 17.7 14.3 32 32 32s32-14.3 32-32l0-48C576 64.5 511.5 0 432 0S288 64.5 288 144l0 48L64 192c-35.3 0-64 28.7-64 64L0 448c0 35.3 28.7 64 64 64l320 0c35.3 0 64-28.7 64-64l0-192c0-35.3-28.7-64-64-64l-32 0 0-48z'/></svg>";

                                usersIntBottom.innerHTML = figures;

                                usersIntMid.innerHTML = login + sta + status + grupos;

                                const grupoDiv = usersIntMid.querySelector(".grupo");
                                const seta = usersIntMid.querySelector(".seta-grupo");

                                seta.addEventListener("click", () => {
                                    const isVisible = grupoDiv.style.display === "block";
                                    grupoDiv.style.display = isVisible ? "none" : "block";
                                    seta.style.transform = isVisible ? "rotate(0deg)" : "rotate(180deg)";
                                    seta.style.transition = "0.5s";
                                });

                                }
                            });
                        }).catch((error)=>{
                            alert(error);
                        });
                    });
            });
    
        }).catch((error) =>{
            alert(error);
        });
    }
});