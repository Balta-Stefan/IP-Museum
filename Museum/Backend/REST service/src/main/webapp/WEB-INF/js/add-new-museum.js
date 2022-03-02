function clearSelect(selectElement)
{
    const options = selectElement.getElementsByTagName("option");

    for(let i = options.length; i > 1; i--)
    {
        selectElement.removeChild(options[i]);
    }
}

async function getRegions()
{
    // clear regions and cities select
    document.getElementById("region").options.length = 0;
    document.getElementById("city").options.length = 0;
    //clearSelect(document.getElementById("region"));
    //clearSelect(document.getElementById("city"));


    const selectedCountry = document.getElementById("country").value;
    document.getElementById("countryAlpha2Code").value = selectedCountry;

    const URL = "./countries/" + selectedCountry + "/regions";

    const response = await fetch(URL);
    const data = await response.json();



    const regionsSelect = document.getElementById("region");

    for(const region of data)
    {
        let tempOpt = document.createElement("option");
        tempOpt.value = region.region;
        tempOpt.innerHTML = region.region;

        regionsSelect.appendChild(tempOpt);
    }
}

async function getCities()
{
    // clear cities select
    document.getElementById("city").options.length = 0;
    //clearSelect(document.getElementById("city"));

    // /countries/{country}/regions/{region}/cities
    const selectedCountry = document.getElementById("country").value;
    const selectedRegion = document.getElementById("region").value;

    const URL = "./countries/" + selectedCountry + "/regions/" + selectedRegion + "/cities";

    const response = await fetch(URL);
    const data = await response.json();



    const citySelect = document.getElementById("city");

    for(const city of data)
    {
        let tempOpt = document.createElement("option");
        tempOpt.value = city.city;
        tempOpt.innerHTML = city.city;

        citySelect.appendChild(tempOpt);
    }
}

async function getCountries()
{
    const countriesURL = "https://restcountries.com/v3.1/region/europe";

    const response = await fetch(countriesURL);
    const data = await response.json();

    const countrySelect = document.getElementById("country");

    for(const country of data)
    {
        let tempOpt = document.createElement("option");
        tempOpt.value = country.cca2;
        tempOpt.innerHTML = country.name.common;

        countrySelect.appendChild(tempOpt);
    }
}

window.addEventListener("load", function(){
    getCountries();

    document.getElementById("country").addEventListener("change", getRegions);
    document.getElementById("region").addEventListener("change", getCities);
});