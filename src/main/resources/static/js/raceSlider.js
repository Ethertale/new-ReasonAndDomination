const slides = document.querySelectorAll('.slides img');
let slideIndex = 0;
let intervalId = null;
const intervalTimer = 5000;
const raceName = document.querySelector('#race-name');
const raceDescription = document.querySelector('#race-description');

initSlider();

function initSlider(){
    if (slides.length > 0){
        slides[slideIndex].classList.add('displaySlide');
        intervalId = setInterval(nextSlide, intervalTimer);
    }
}

function showSlide(index){

    if (index >= slides.length){
        slideIndex = 0;
    }
    else if(index < 0){
        slideIndex = slides.length-1;
    }

    slides.forEach((slide) => {
        slide.classList.remove('displaySlide');
    })
    slides[slideIndex].classList.add('displaySlide');
}

function previousSlide(){
    clearInterval(intervalId);
    slideIndex--;
    showSlide(slideIndex)
}

function nextSlide(){
    clearInterval(intervalId);
    slideIndex++;
    showSlide(slideIndex);
}

function textReplacer(){
    switch (slideIndex.attributes.type){
        //TODO IMAGE SLIDER TEXT AND DESCRIPTION
    }
}