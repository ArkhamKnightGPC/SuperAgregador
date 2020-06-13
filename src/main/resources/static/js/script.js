function toggleFormAdicionarItens() {
    var formAdicionarItens = document.getElementById("adicionar");

    if (formAdicionarItens.classList.contains("hide")) {
        document.getElementById("adicionar").style.display = "block";
        setTimeout(
            () => {
                document.getElementById("adicionar").classList.remove("hide");
            }, 100);
        document.getElementById("nav-adc").setAttribute("href", "/#adicionar");
        
    } else {
        formAdicionarItens.classList.add("hide");
        setTimeout( () => {
            document.getElementById("adicionar").style.display = "none";
        } ,500);

        document.getElementById("nav-adc").setAttribute("href", "/#");
    }
}

function toggleListarFontesCadastradas() {
    var btnListarFontes = document.getElementById("listar-fontes");
    var btnDeslistarFontes = document.getElementById("deslistar-fontes");
    var divFontes = document.getElementById("fontes");

    if (btnListarFontes.classList.contains("hide")) {
        btnListarFontes.style.display = "block";
        btnDeslistarFontes.style.display = "none";
        divFontes.style.display = "none";
        btnListarFontes.classList.remove("hide");
    } else {
        btnListarFontes.style.display = "none";
        btnDeslistarFontes.style.display = "block";
        divFontes.style.display = "block";
        btnListarFontes.classList.add("hide");
    }

}