const ranges = [
    {label:"7000-8000", min:7000, max:8000},
    {label:"15000-16000", min:15000, max:16000},
    {label:"23000-24000", min:23000, max:24000},
    {label:"31000-32000", min:31000, max:32000},
    {label:"39000-40000", min:39000, max:40000},
    {label:"47000-48000", min:47000, max:48000},
    {label:"51000-52000", min:51000, max:52000}
];

const token = localStorage.getItem("token");

async function generateReport(){

    let counts = [];

    for(const r of ranges){

        const response = await fetch(
        `http://localhost:8080/api/moulds/dashboard-range?min=${r.min}&max=${r.max}`,
        {
            headers:{
                "Authorization":"Bearer "+token
            }
        });

        const data = await response.json();

        counts.push(data.length);
    }

    drawChart(counts);
}

function drawChart(counts){

    const ctx = document.getElementById("chart");

    new Chart(ctx,{
        type:"bar",
        data:{
            labels:ranges.map(r=>r.label),
            datasets:[{
                label:"Mould Count",
                data:counts
            }]
        },
        options:{
            onClick:function(evt,items){

                if(items.length>0){

                    const index = items[0].index;

                    const r = ranges[index];

                    window.location.href =
                    `range-table.html?min=${r.min}&max=${r.max}`;
                }
            }
        }
    });

}