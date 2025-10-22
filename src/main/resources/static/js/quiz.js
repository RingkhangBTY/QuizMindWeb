const quizArea = document.getElementById('quiz-area');
const submitBtn = document.getElementById('submit');
const resultDiv = document.getElementById('result');
const historyList = document.getElementById('history-list');

let currentQuestions = [];

document.getElementById('logout').addEventListener('click', () => {
    window.location.href = '/logout';
});

document.getElementById('generate').addEventListener('click', async () => {
    const input = {
        programmingLanguage_Subject: document.getElementById('subject').value,
        shortDes_Topic_Concepts: document.getElementById('topic').value,
        level: document.getElementById('level').value,
        noOfQ: parseInt(document.getElementById('noq').value)
    };

    const res = await fetch('/start', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(input)
    });

    if (res.ok) {
        const data = await res.json();
        currentQuestions = data;
        renderQuiz(data);
    } else {
        quizArea.textContent = 'Error fetching questions.';
    }
});

function renderQuiz(questions) {
    quizArea.innerHTML = '';
    resultDiv.innerHTML = '';
    submitBtn.classList.remove('hidden');

    questions.forEach((q, idx) => {
        const div = document.createElement('div');
        div.classList.add('question');
        div.innerHTML = `<p><b>${idx + 1}. ${q.question}</b></p>`;
        ['A','B','C','D'].forEach(opt => {
            const val = q[`option${opt}`];
            const id = `q${idx}_${opt}`;
            div.innerHTML += `
        <label><input type="radio" name="q${idx}" value="${val}" id="${id}"> ${val}</label><br>
      `;
        });
        quizArea.appendChild(div);
    });
}

submitBtn.addEventListener('click', async () => {
    const answered = currentQuestions.map((q, i) => {
        const sel = document.querySelector(`input[name="q${i}"]:checked`);
        return { ...q, userAnswer: sel ? sel.value : null };
    });

    const res = await fetch('/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(answered)
    });

    if (res.ok) {
        const result = await res.json();
        resultDiv.innerHTML = `
      <p><b>Score:</b> ${result.Score}</p>
      <p><b>Correct:</b> ${result.CorrectAnswers}/${result.TotalQuestions}</p>
      <p><b>Comment:</b> ${result.comment}</p>
    `;
        submitBtn.classList.add('hidden');
    }
});

document.getElementById('load-history').addEventListener('click', async () => {
    const res = await fetch('/scoreHistory', { credentials: 'include' });
    if (!res.ok) {
        historyList.textContent = 'Failed to load history.';
        return;
    }

    const data = await res.json();
    historyList.innerHTML = '';
    data.forEach(h => {
        const div = document.createElement('div');
        div.classList.add('small');
        div.innerHTML = `
      <p>${h.topic_sub || h.short_des} — Score: ${h.test_score} (${h.correct_ans}/${h.total_question})</p>
      <p>${new Date(h.time_stamp).toLocaleString()}</p>
    `;
        historyList.appendChild(div);
    });
});
