(function () {
    "use strict";
    let buttonsGroups = document.getElementsByClassName("btn-group");
    for (let i = 0; i < buttonsGroups.length; i++) {
        let buttonGroup = buttonsGroups[i];
        let buttons = buttonGroup.getElementsByClassName("btn");
        for (let j = 0; j < buttons.length; j++) {
            let button = buttons[j];
            button.addEventListener('click', async function () {
                let request = {
                    "evaluation": button.value,
                    "questionnaireId": button.getAttribute("questionnaire-id"),
                    "questionId": button.getAttribute("question-id")
                };

                let url = "http://62.109.13.45:8080/estimate";
                const response = await fetch(url, {
                    method: 'POST', headers: {
                        'Accept': 'application/json', 'Content-Type': 'application/json'
                    }, body: JSON.stringify(request)
                });

                await response.blob().then(() => {
                    if (response.ok) {
                        let activeBtn = buttonGroup.getElementsByClassName("active")[0];
                        if (activeBtn === undefined) {
                            button.classList.add("active")
                        } else {
                            activeBtn.classList.remove("active");
                            button.classList.add("active")
                        }
                    } else {
                        console.log("Error estimate", response);
                    }
                });
            });
        }
    }
})();

function copyToClipboardImproveTopics() {
    const improvesTopic = document.getElementsByClassName('improve-topic');
    let resultText = '';
    for (let i = 0; i < improvesTopic.length; i++) {
        resultText += improvesTopic[i].innerText;
        if ((i + 1) !== improvesTopic.length) {
            resultText += '\r';
        }
    }

    try {
        document.execCommand('copy');
    } catch (err) {
        console.error('Unable to copy to clipboard', err);
    }

    if (window.isSecureContext && navigator.clipboard) {
        navigator.clipboard.writeText(resultText).then(r => console.log("Copied the text: " + r));
    } else {
        console.info("Use deprecated");
        unsecuredCopyToClipboard(resultText);
    }
}

function unsecuredCopyToClipboard(text) {
    const textArea = document.createElement("textarea");
    textArea.value = text;
    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();
    try {
        document.execCommand('copy');
    } catch (err) {
        console.error('Unable to copy to clipboard', err);
    }
    document.body.removeChild(textArea);
}
