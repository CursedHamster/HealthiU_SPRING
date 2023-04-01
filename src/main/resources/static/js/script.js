let startButton = document.getElementById("start_part0");
let nextButton1 = document.getElementById("next_part1");
let previousButton2 = document.getElementById("previous_part2");
let nextButton2 = document.getElementById("next_part2");
let previousButton3 = document.getElementById("previous_part3");
let nextButton3 = document.getElementById("next_part3");
let previousButton4 = document.getElementById("previous_part4");
let nextButton4 = document.getElementById("next_part4");
let previousButton5 = document.getElementById("previous_part5");
let nextButton5 = document.getElementById("next_part5");
let previousButton6 = document.getElementById("previous_part6");
let nextButton6 = document.getElementById("next_part6");
let previousButton7 = document.getElementById("previous_part7");
let nextButton7 = document.getElementById("next_part7");
let previousButton8 = document.getElementById("previous_part8");


let part0 = document.getElementById("part0");
let part1 = document.getElementById("part1");
let part2 = document.getElementById("part2");
let part3 = document.getElementById("part3");
let part4 = document.getElementById("part4");
let part5 = document.getElementById("part5");
let part6 = document.getElementById("part6");
let part7 = document.getElementById("part7");
let part8 = document.getElementById("part8");

let errorAge = $("#error_age");
let errorHeight = $("#error_height");
let errorWeight = $("#error_weight");
let errorChestSize = $("#error_chest_size");
let errorWaistSize = $("#error_waist_size");
let errorHipSize = $("#error_hip_size");


startButton.addEventListener("click", function (e) {
    e.preventDefault();
    part0.classList.add("hidden");
    part1.classList.remove("hidden");
})

nextButton1.addEventListener("click", function (e) {
    e.preventDefault();
    part1.classList.add("hidden");
    part2.classList.remove("hidden");
})

previousButton2.addEventListener("click", function (e) {
    e.preventDefault();
    part1.classList.remove("hidden");
    part2.classList.add("hidden");
})

nextButton2.addEventListener("click", function (e) {
    e.preventDefault();
    let age = $("#age").val();
    console.log(age);
    errorAge.text(" ");
    if (age < 14 || age > 130) {
        errorAge.text(
            "Проходити цей тест можна лише людям з 14 років"
        );
    } else {
        part2.classList.add("hidden");
        part3.classList.remove("hidden");
    }
})

previousButton3.addEventListener("click", function (e) {
    e.preventDefault();
    part2.classList.remove("hidden");
    part3.classList.add("hidden");
})

nextButton3.addEventListener("click", function (e) {
    e.preventDefault();
    let height = $("#height").val();
    errorHeight.text(" ");
    if (isNaN(height)) {
        errorHeight.text("Ріст можна вводити лише цифровим значенням");
    } else if (height < 50) {
        errorHeight.text("Некоректний ріст");
    } else {
        part3.classList.add("hidden");
        part4.classList.remove("hidden");
    }
})

previousButton4.addEventListener("click", function (e) {
    e.preventDefault();
    part3.classList.remove("hidden");
    part4.classList.add("hidden");
})

nextButton4.addEventListener("click", function (e) {
    e.preventDefault();
    let weight = $("#weight").val();
    errorWeight.text(" ");
    if (isNaN(weight)) {
        errorWeight.text("Вагу можна вводити лише цифровим значенням");
    } else if (weight < 5) {
        errorWeight.text("Некоректна вага");
    } else {
        part4.classList.add("hidden");
        part5.classList.remove("hidden");
    }
})

previousButton5.addEventListener("click", function (e) {
    e.preventDefault();
    part4.classList.remove("hidden");
    part5.classList.add("hidden");
})

nextButton5.addEventListener("click", function (e) {
    e.preventDefault();
    let chestSize = $("#chest_size").val();
    errorChestSize.text(" ");
    if (isNaN(chestSize)) {
        errorChestSize.text("Обхват грудей можна вводити лише цифровим значенням");
    } else if (chestSize < 20) {
        errorChestSize.text("Некоректний обхват грудей");
    } else {
        part5.classList.add("hidden");
        part6.classList.remove("hidden");
    }
})

previousButton6.addEventListener("click", function (e) {
    e.preventDefault();
    part5.classList.remove("hidden");
    part6.classList.add("hidden");
})

nextButton6.addEventListener("click", function (e) {
    e.preventDefault();
    let waistSize = $("#waist_size").val();
    errorWaistSize.text(" ");
    if (isNaN(waistSize)) {
        errorWaistSize.text("Обхват талії можна вводити лише цифровим значенням");
    } else if (waistSize < 20) {
        errorWaistSize.text("Некоректний обхват талії");
    } else {
        part6.classList.add("hidden");
        part7.classList.remove("hidden");
    }
})

previousButton7.addEventListener("click", function (e) {
    e.preventDefault();
    part6.classList.remove("hidden");
    part7.classList.add("hidden");
})

nextButton7.addEventListener("click", function (e) {
    e.preventDefault();
    let hipSize = $("#hip_size").val();
    errorHipSize.text(" ");
    if (isNaN(hipSize)) {
        errorHipSize.text("Invalid hip size");
    } else if (hipSize < 1) {
        errorHipSize.text("Invalid hip size");
    } else {
        part7.classList.add("hidden");
        part8.classList.remove("hidden");
    }
})
