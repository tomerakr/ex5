window.onload = function () {
    const guessInputs = document.querySelectorAll('.guess-input');
    guessInputs.forEach(function (input) {
        if (input.getAttribute('data-active-row') === 'true') {
            input.classList.add('active');
            if (!input.disabled) {
                input.focus();
            }
        }

        input.addEventListener('input', updateCombinedGuess);
    });
};

function updateCombinedGuess() {
    let guess = '';
    const guessInputs = document.querySelectorAll('.guess-input.active');

    guessInputs.forEach(function (input) {
        guess += input.value.toUpperCase();
    });
    document.getElementById('combinedGuess').value = guess;
}

function handleInput(input, cellIndex, rowIndex) {
    if (!/^[a-zA-Z]*$/g.test(input.value)) {
        input.value = "";
        return;
    }
    if(input.value.length === 1 && cellIndex !== 4) {
        input.disabled = true;
        enableNextInput(rowIndex, cellIndex);
    }
}

function enableNextInput(row, cell) {
    let nextCell = cell + 1;
    if (nextCell < 5) { //change it to (guess_size - 1)
        let nextInputId = 'input-' + row + '-' + nextCell;
        let nextInput = document.getElementById(nextInputId);
        nextInput.disabled = false;
        nextInput.focus(); // Set focus
    }
}

function goBack(row, cell) {
    let prevCell = cell - 1;
    if (prevCell >= 0) {
        let currentInputId = 'input-' + row + '-' + cell;
        let currentInput = document.getElementById(currentInputId);
        if (currentInput.value.length === 1) {
            currentInput.value = '';
        }
        else {
            currentInput.disabled = true;
            let prevInputId = 'input-' + row + '-' + prevCell;
            let prevInput = document.getElementById(prevInputId);
            prevInput.disabled = false;
            prevInput.value = '';
            prevInput.focus(); // Set focus
        }
    }
    updateCombinedGuess();
}

function handleButtonClick(letter) {
    // Find the currently active cell
    let activeCell = document.querySelector('.active');
    if (activeCell) {
        // Trigger the input handler for the active cell
        let idParts = activeCell.id.split('-');
        let rowIndex = parseInt(idParts[1]);
        let cellIndex = parseInt(idParts[2]);

        // Check if the active cell is disabled and find the next non-disabled cell if necessary
        while (activeCell.disabled && cellIndex < 5) {  // Assuming maximum of 5 cells
            cellIndex++;
            activeCell = document.getElementById('input-' + rowIndex + '-' + cellIndex);
        }

        if (letter === 'Enter') {
            if (4 === cellIndex)
            {
                let form = document.getElementById('wordForm');
                form.submit();
            }
        } else if (letter === 'Delete') {
            // If the Delete button was pressed, clear the active cell
            goBack(rowIndex, cellIndex);
        } else {
            // Set the value of the active cell to the letter from the button
            activeCell.value = letter;
            handleInput(activeCell, cellIndex, rowIndex);
            updateCombinedGuess();
        }
    }
}
