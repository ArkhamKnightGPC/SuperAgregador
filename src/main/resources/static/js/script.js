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

const searchBar = document.forms['search-name'].querySelector('input');
searchBar.addEventListener('keyup', function(e){
    const term = e.target.value.toLowerCase();
    const articles = document.getElementsByClassName('card border-dark mx-auto noticia');
    Array.from(articles).forEach(function(article){
        const firstdiv = article.firstElementChild;
        const title = firstdiv.firstElementChild.textContent.toLowerCase(); //pega o titulo
        const c = article.children;
        k = c[2].children;  //pega o elemento pai da descrição o k[1] é o segundo <p>, lugar a ser procurado
        if(k[1].textContent.toLowerCase().indexOf(term) != -1 || title.indexOf(term) != -1){
            article.style.display = 'block';
        }else{
            article.style.display = 'none';
        }
    })
})