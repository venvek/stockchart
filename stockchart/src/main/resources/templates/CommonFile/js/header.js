document.addEventListener("DOMContentLoaded", function () {
    const searchBox = document.getElementById("searchBox");
    const searchResults = document.getElementById("searchResults");
    const form = document.getElementById("stockSearchForm");

    // URL 파라미터에서 ticker 가져오기 → 페이지 로드시 차트 로딩
    const params = new URLSearchParams(window.location.search);
    const ticker = params.get("ticker");
    if (ticker) {
        loadStockChart(ticker);
    }

    // 검색창 입력 시 자동완성
    searchBox.addEventListener("input", async (e) => {
        const query = e.target.value.trim();
        if (!query) {
            searchResults.innerHTML = "";
            searchResults.style.display = "none";
            return;
        }

        const res = await fetch(`/api/search/window?q=${encodeURIComponent(query)}`);
        const data = await res.json();

        let html = "";
        if (data.results.length > 0) {
            html += "<ul style='list-style:none; padding:0; margin:0;'>";
            data.results.forEach(item => {
                html += `
                    <li style="padding: 6px; cursor: pointer;"
                        onclick="goToTicker('${item.ticker}')">
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

    // 검색창 직접 입력 후 엔터 → 이동
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        const newTicker = searchBox.value.trim();
        if (newTicker) {
            goToTicker(newTicker);
        }
    });

});

// 자동완성 항목 선택 시 이동
function goToTicker(ticker) {
    window.location.href = `/stocks/${encodeURIComponent(ticker)}`;
}

// 차트 로딩
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
