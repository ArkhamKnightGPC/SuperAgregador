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

const articles = document.getElementsByClassName('card border-dark mx-auto noticia');
//funcao auxiliar para retirar uma array de strings entre beg e end de uma string
function extract([beg, end]) {
    const matcher = new RegExp(`${beg}(.*?)${end}`,'gm');
    const normalise = (str) => str.slice(beg.length,end.length*-1);
    return function(str) {
        return str.match(matcher).map(normalise);
    }
}

function regexFromString (string) {
    var match = /^\/(.*)\/([a-z]*)$/.exec(string)
    return new RegExp(match[1], match[2])
  }

function toggleBusca() {
    const div = document.getElementById('searching');
    var stringComplete = div.value.toLowerCase();
    if(stringComplete.includes('"')){
        const stringsComAspas = extract([`"`,`"`])(stringComplete);
        stringsComAspas.forEach(function(string1){
            stringComplete = stringComplete.replace('"' + string1 + '"', '')
        })
        var stringsSemAspas = stringComplete.trim().split(/\s+/);
        if(stringsSemAspas[0] != ''){
            var todasStrings = stringsSemAspas.concat(stringsComAspas);
        }else{
            var todasStrings = stringsComAspas.slice()
        }
    }else{
        var todasStrings = stringComplete.trim().split(/\s+/);
    }
    
    Array.from(articles).forEach(function(article){
        const firstdiv = article.firstElementChild;
        const title = firstdiv.firstElementChild.textContent.toLowerCase(); //pega o titulo
        const c = article.children;
        k = c[2].children;  //pega o elemento pai da descrição, o k[0] é o primeiro <p>, lugar a ser procurado  
        article.style.display = 'none';
        todasStrings.forEach(function(term){
            if(term.includes("/")){
                var regexp = regexFromString(term);
                if(regexp.test(k[0].textContent.toLowerCase()) || regexp.test(title) ){
                    article.style.display = 'block';
                }
            }
            else if(k[0].textContent.toLowerCase().includes(term) || title.includes(term) ){
                article.style.display = 'block';
            }
        })
        })
}
//enter para o input funcionar
function searchKeyPress(e)
{
    e = e || window.event;
    if (e.keyCode == 13)
    {
        document.getElementById('botaoSearch').click();
        return false;
    }
    return true;
}
//atualiza a página para deixar sempre as noticias mais recentes
setTimeout(function() { 
    window.location.reload(1);
  }, 180000);

//dinamicidade do botão filtrar por data
function toggleBuscarPorData(){
    const myDiv = document.getElementById("dinamicData");
    if (myDiv.style.display === "block") {
        myDiv.style.display = "none";
      } else {
        myDiv.style.display = "block";
      }
}

//Tirando o fuso das data pra exibir mais bonito
const dates = document.getElementsByClassName('data1');
    Array.from(dates).forEach(function(date){
        let array= date.textContent.split('+').join('-').split('-');
        date.innerHTML = array[0];

    })

//analisando as datas
const dateinicio = document.getElementById('dateinicio');
const datefinal = document.getElementById('datefinal');
const form = document.getElementById('formdate');

form.addEventListener('submit', function(ev){
    ev.preventDefault();

    //pegando os valores das datas de entrada
    let anoinicio = dateinicio.value.substr(0,4)
    let mesinicio = dateinicio.value.substr(5,2)
    let diainicio = dateinicio.value.substr(8,2)
    let horainicio = dateinicio.value.substr(11,2)
    let minutoinicio = dateinicio.value.substr(14,2)

    let anofinal = datefinal.value.substr(0,4)
    let mesfinal = datefinal.value.substr(5,2)
    let diafinal = datefinal.value.substr(8,2)
    let horafinal = datefinal.value.substr(11,2)
    let minutofinal = datefinal.value.substr(14,2)


    Array.from(articles).forEach(function(article){
        const blocos = article.children;
        let datainteira = blocos[2].children[1].textContent;
        let dataformatada = datainteira.match(/\d{2}\s\w{3}\s\d{4}\s(\d{2}:){2}(\d{2})/g); //sai um objeto
        let dia = dataformatada[0].match(/^\d{2}/g);
        let month = dataformatada[0].match(/[a-zA-Z]{3}/g);
        let resultmonth = month[0].toLowerCase().trim();
        let mes = 0;
        if ("jan".localeCompare(resultmonth) === 0){
            mes = 1;
        }else
        if ("fev".localeCompare(resultmonth) === 0 || "feb".localeCompare(resultmonth) === 0){
            mes = 2;
        }else
        if ("mar".localeCompare(resultmonth) === 0){
            mes = 3;
        }else
        if ("apr".localeCompare(resultmonth) === 0 || "abr".localeCompare(resultmonth) === 0){
            mes = 4;
        }else
        if ("may".localeCompare(resultmonth) === 0 || "mai".localeCompare(resultmonth) === 0 ) {
            mes = 5;
        }else
        if ("jun".localeCompare(resultmonth) === 0){
            mes = 6;
        }else
        if ("jul".localeCompare(resultmonth) === 0){
            mes = 7;
        }else
        if ("aug".localeCompare(resultmonth) === 0 || "ago".localeCompare(resultmonth) === 0){
            mes = 8;
        }else
        if ("sep".localeCompare(resultmonth) === 0 || "set".localeCompare(resultmonth) === 0){
            mes = 9;
        }else
        if ("oct".localeCompare(resultmonth) === 0 || "out".localeCompare(resultmonth) === 0){
            mes = 10;
        }else
        if ("nov".localeCompare(resultmonth) === 0){
            mes = 11;
        }else
        if ("dec".localeCompare(resultmonth) === 0 || "dez".localeCompare(resultmonth) === 0){
            mes = 12;
        }
        //todos saem como objetos, necessita o [0] para pegar a string correspondente
        let ano = dataformatada[0].match(/\d{4}/g);
        let horacompleta = dataformatada[0].match(/\d{2}:\d{2}/g);
        let hora = horacompleta[0].match(/^\d{2}/g);
        let minute = horacompleta[0].match(/\d{2}$/g);
        
        //comeca aqui a comparacao para saber se data esta no range escolhido pelo usuario
        article.style.display = 'none'; //desaparece com o article, só aparece de novo se for satisfeita as condicoes abaixo

        let dataInicioTimestamps = new Date();
        dataInicioTimestamps.setFullYear(parseInt(anoinicio))
        dataInicioTimestamps.setMonth(parseInt(mesinicio)-1)
        dataInicioTimestamps.setDate(parseInt(diainicio))
        dataInicioTimestamps.setHours(parseInt(horainicio))
        dataInicioTimestamps.setMinutes(parseInt(minutoinicio))
        dataInicioTimestamps.setSeconds(0)

        let dataFinalTimestamps = new Date();
        dataFinalTimestamps.setFullYear(parseInt(anofinal))
        dataFinalTimestamps.setMonth(parseInt(mesfinal)-1)
        dataFinalTimestamps.setDate(parseInt(diafinal))
        dataFinalTimestamps.setHours(parseInt(horafinal))
        dataFinalTimestamps.setMinutes(parseInt(minutofinal))
        dataFinalTimestamps.setSeconds(0)

        let dataTimestamps = new Date();
        dataTimestamps.setFullYear(parseInt(ano[0]))
        dataTimestamps.setMonth(mes-1)
        dataTimestamps.setDate(parseInt(dia[0]))
        dataTimestamps.setHours(parseInt(hora[0]))
        dataTimestamps.setMinutes(parseInt(minute[0]))
        dataTimestamps.setSeconds(0)


        if ( dataTimestamps.getTime() < dataFinalTimestamps.getTime() && dataTimestamps.getTime() > dataInicioTimestamps.getTime() ){
            article.style.display = 'block';
        }


    })


})
