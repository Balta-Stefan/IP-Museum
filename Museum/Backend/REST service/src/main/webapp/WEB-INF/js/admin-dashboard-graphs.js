

function prepareCharts()
{
    const data = {
        labels: xLabels,
        datasets: [{
            label: 'Broj prijavljenih korisnika po satima tekućeg dana',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: usersPerHoursArray,
        }]
    };

    const config = {
        type: 'bar',
        data: data,
        options: {
            maintainAspectRatio: false,
            responsive: true
        }
    };

    const myChart = new Chart(
        document.getElementById('users_chart'),
        config
    );

}

window.addEventListener("load", prepareCharts);