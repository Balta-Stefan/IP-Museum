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
    const selectedCountry = document.getElementById("country").value;
    document.getElementById("countryAlpha2Code").value = selectedCountry;

    const URL = "./countries/" + selectedCountry + "/regions";

    const response = await fetch(URL);
    const data = await response.json();

    clearSelect(document.getElementById("region"));

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
    // /countries/{country}/regions/{region}/cities
    const selectedCountry = document.getElementById("country").value;
    const selectedRegion = document.getElementById("region").value;

    const URL = "./countries/" + selectedCountry + "/regions/" + selectedRegion + "/cities";

    const response = await fetch(URL);
    const data = await response.json();

    clearSelect(document.getElementById("city"));

    const citySelect = document.getElementById("city");

    for(const city of data)
    {
        let tempOpt = document.createElement("option");
        tempOpt.value = city.city;
        tempOpt.innerHTML = city.city;

        citySelect.appendChild(tempOpt);
    }
}

window.addEventListener("load", function(){
   document.getElementById("country").addEventListener("change", getRegions);
   document.getElementById("region").addEventListener("change", getCities);
});