document.addEventListener("DOMContentLoaded", function() { 
    const params = new URLSearchParams(window.location.search);
    const ticker = params.get("ticker");

    if (ticker) {
        loadStockChart(ticker);
    }

    // 검색창 submit 처리
    const form = document.getElementById("stockSearchForm");
    const searchBox = document.getElementById("searchBox");
	
	form.addEventListener("submit", function(event) {
	    event.preventDefault();
	    const newTicker = searchBox.value.trim();
	    if (newTicker) {
	        // PathVariable 방식 이동
			window.location.href = "/stocks/" + encodeURIComponent(ticker);
			            }
	});

async function loadStockChart(ticker) {
    const res = await fetch(`/api/stock-data?ticker=${encodeURIComponent(ticker)}`);
    const data = await res.json();

    const ctx = document.getElementById("stockChart").getContext("2d");

    new Chart(ctx, {
        type: "line",
        data: {
            labels: data.dates,
            datasets: [{
                label: ticker,
                data: data.closes,
                borderColor: "blue",
                fill: false
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { display: true, title: { display: true, text: "Date" } },
                y: { display: true, title: { display: true, text: "Price" } }
            }
        }
    });
}
