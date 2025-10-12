const searchBox = document.getElementById("searchBox");
const searchResults = document.getElementById("searchResults");
const form = document.getElementById("stockSearchForm");

// 자동완성 (티커 / 회사명 표시)
searchBox.addEventListener("input", async (e) => {
    const query = e.target.value.trim();
    if (!query) {
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
        return;
    }

    const res = await fetch(`/api/search/window?q=${encodeURIComponent(query)}`);
    const data = await res.json();

    if (data.results.length > 0) {
        let html = "<ul style='list-style:none; padding:0; margin:0; border:1px solid #ccc; background:white;'>";
        data.results.forEach(item => {
            html += `
                <li style="padding:6px; cursor:pointer;"
                    onclick="goToTicker('${item.ticker}')"
                    onmouseover="this.style.backgroundColor='#eee'"
                    onmouseout="this.style.backgroundColor='white'">
                    <strong>${item.ticker}</strong> / ${item.companyName}
                </li>`;
        });
        html += "</ul>";
        searchResults.innerHTML = html;
        searchResults.style.display = "block";
    } else {
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
    }
});

// 클릭 시 이동
function goToTicker(ticker) {
    window.location.href = `/stocks/${encodeURIComponent(ticker)}`;
}

// 엔터 입력 시 처리
form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const keyword = searchBox.value.trim();
    if (!keyword) return;

    // 서버에 존재 여부 확인 요청
    const res = await fetch(`/api/search/check?tickerOrName=${encodeURIComponent(keyword)}`);
    const data = await res.json();

    if (data.exists) {
        window.location.href = `/stocks/${encodeURIComponent(data.ticker)}`;
    } else {
        // 🚫 팝업 경고창 띄우기
        showPopup(`"${keyword}"에 해당하는 종목을 찾을 수 없습니다.`);
    }
});

// 간단한 팝업 함수
function showPopup(message) {
    const popup = document.createElement("div");
    popup.textContent = message;
    popup.style.position = "fixed";
    popup.style.top = "20px";
    popup.style.left = "50%";
    popup.style.transform = "translateX(-50%)";
    popup.style.background = "#ffdddd";
    popup.style.color = "#333";
    popup.style.padding = "10px 20px";
    popup.style.border = "1px solid #f00";
    popup.style.borderRadius = "6px";
    popup.style.boxShadow = "0 2px 6px rgba(0,0,0,0.2)";
    popup.style.zIndex = "9999";
    document.body.appendChild(popup);

    // 2초 후 자동 사라짐
    setTimeout(() => popup.remove(), 2000);
}
