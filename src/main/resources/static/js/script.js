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