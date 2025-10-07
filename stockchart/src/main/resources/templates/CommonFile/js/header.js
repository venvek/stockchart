const searchResults = document.getElementById("searchResults");

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

	searchBox.addEventListener("input", async (e) => {
	    const query = e.target.value.trim();
	    if (!query) {
	        searchResults.innerHTML = "";
	        searchResults.style.display = "none";
	        return;
	    }

	    // 서버에서 데이터 가져오기
	    const res = await fetch(`/api/search/window?q=${encodeURIComponent(query)}`);
	    const data = await res.json();

	    // 결과 렌더링 (ticker + 회사명 같이 표시)
	    let html = "";
	    if (data.results.length > 0) {
	        html += "<ul style='list-style:none; padding:0; margin:0;'>";
	        data.results.forEach(item => {
	            html += `
	                <li style="padding: 6px; cursor: pointer;"
	                    onclick="selectSuggestion('${item.ticker}')">
	                    <strong>${item.ticker}</strong> – ${item.companyName}
	                </li>`;
	        });
	        html += "</ul>";
	        searchResults.innerHTML = html;
	        searchResults.style.display = "block";
	    } else {
	        searchResults.style.display = "none";
	    }
	});

	// 자동완성 항목 선택 처리
	function selectSuggestion(value) {
	    searchBox.value = value;
	    searchResults.innerHTML = "";
	    searchResults.style.display = "none";
	}
	
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
}