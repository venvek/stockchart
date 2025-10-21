const searchBox = document.getElementById("searchBox");
const searchResults = document.getElementById("searchResults");
const form = document.getElementById("stockSearchForm");

// 🔍 자동완성
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
        let html = "<ul style='list-style:none; padding:0; margin:0; border:1px solid #ccc; background:white; border-radius:6px; box-shadow:0 2px 6px rgba(0,0,0,0.15);'>";
        data.results.forEach(item => {
            html += `
                <li style="padding:8px 10px; cursor:pointer; border-bottom:1px solid #eee;"
                    onclick="goToTicker('${item.ticker}')"
                    onmouseover="this.style.backgroundColor='#f5f5f5'"
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

async function recordSearch(ticker) {
    try {
        await fetch(`/api/search/record?ticker=${encodeURIComponent(ticker)}`, {
            method: "POST"
        });
    } catch (err) {
        console.error("검색 기록 저장 실패:", err);
    }
}

function goToTicker(ticker) {
    recordSearch(ticker); // 서버 기록
    window.location.href = `/stocks/${encodeURIComponent(ticker)}`;
}

// ⌨️ 엔터 입력 시 처리
form.addEventListener("submit", async (event) => {
    
    const keyword = searchBox.value.trim();
    if (!keyword) return;

    const res = await fetch(`/api/search/check?tickerOrName=${encodeURIComponent(keyword)}`);
    const data = await res.json();

    if (data.exists) {
        window.location.href = `/stocks/${encodeURIComponent(data.ticker)}`;
    } else {
        showModal(`"${keyword}"에 해당하는 종목을 찾을 수 없습니다.`);
    }
});

// 💬 모달 표시 함수
function showModal(message) {
    // 기존 모달이 있으면 제거
    const existing = document.getElementById("notFoundModal");
    if (existing) existing.remove();

    const modal = document.createElement("div");
    modal.id = "notFoundModal";
    modal.innerHTML = `
        <div style="
            position: fixed; top: 0; left: 0; width: 100%; height: 100%;
            background: rgba(0, 0, 0, 0.4);
            display: flex; align-items: center; justify-content: center;
            z-index: 9999;
        ">
            <div style="
                background: white;
                padding: 20px 30px;
                border-radius: 12px;
                text-align: center;
                box-shadow: 0 4px 10px rgba(0,0,0,0.3);
                max-width: 320px;
            ">
                <h3 style="margin-bottom: 10px; color: #333;">❌ 검색 결과 없음</h3>
                <p style="margin-bottom: 20px; color: #666;">${message}</p>
                <button id="modalCloseBtn" style="
                    padding: 8px 16px;
                    background: #007bff;
                    color: white;
                    border: none;
                    border-radius: 6px;
                    cursor: pointer;
                ">확인</button>
            </div>
        </div>
    `;

    document.body.appendChild(modal);

    // 닫기 버튼 클릭 시 모달 제거
    document.getElementById("modalCloseBtn").addEventListener("click", () => {
        modal.remove();
    });

    // 배경 클릭 시 닫기
    modal.addEventListener("click", (e) => {
        if (e.target === modal) modal.remove();
    });
}
