const SERVER_URL = "http://localhost:8080";

(function ($) {
    $(document).ready(function () {
        "use strict";


        /* MENU TOGGLE */
        $('.mobile-menu .site-menu ul li i').on('click', function (e) {
            $(this).parent().children('.mobile-menu .site-menu ul li ul').toggle();
            return true;
        });


        // FIRST ELEMENTS
        $('.sidebar .widget .gallery-item a').attr('data-fancybox', '');
        $('.accordion .card:first-child .card-header a').attr('aria-expanded', 'true');
        $(".accordion .card:first-child .collapse").addClass("show");
        $(".nav.nav-tabs li:first-child a").addClass("active");
        $(".tab-content .tab-pane:first-child").addClass("active");

        // HAMBURGER MENU
        $('.hamburger-menu').on('click', function (e) {
            $(".hamburger-menu .hamburger").toggleClass('opened');
            $(".mobile-menu").toggleClass('active');
            $("body").toggleClass("overflow");
        });


        // NAVBAR SEARCH
        $('.navbar-search').on('click', function (e) {
            $(".search-box").toggleClass('active');
            $("body").toggleClass("overflow");
        });


        // MAIN SPACER
        $(window).on('resize', function () {
            var mastHeight = $('.page-header, .slider').outerHeight();
            $('main').css('margin-top', mastHeight);
        });

        $(window).trigger('resize');


    });
    // END DOCUMENT READY


    var swiper = new Swiper('.carousel-tv-shows', {

        slidesPerView: 4, spaceBetween: 30, navigation: {
            nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev',
        }, observer: true, observeParents: true, breakpoints: {
            1024: {
                slidesPerView: 3
            }, 768: {
                slidesPerView: 2
            }, 640: {
                slidesPerView: 1
            }, 320: {
                slidesPerView: 1
            }
        }
    });


    // SLIDER
    var interleaveOffset = 0.5;
    var swiperOptions = {
        loop: true, speed: 500, parallax: true, autoplay: {
            delay: 5000, disableOnInteraction: false,
        }, grabCursor: true, watchSlidesProgress: true, pagination: {
            el: '.swiper-pagination', clickable: true, renderBullet: function (index, className) {
                return '<span class="' + className + '"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 30 30"><circle r="13" cy="15" cx="15"></circle></svg></span>';
            },
        }, on: {
            progress: function () {
                var swiper = this;
                for (var i = 0; i < swiper.slides.length; i++) {
                    var slideProgress = swiper.slides[i].progress;
                    var innerOffset = swiper.width * interleaveOffset;
                    var innerTranslate = slideProgress * innerOffset;
                    swiper.slides[i].querySelector(".slide-inner").style.transform = "translate3d(" + innerTranslate + "px, 0, 0)";
                }
            }, touchStart: function () {
                var swiper = this;
                for (var i = 0; i < swiper.slides.length; i++) {
                    swiper.slides[i].style.transition = "";
                }
            }, setTransition: function (speed) {
                var swiper = this;
                for (var i = 0; i < swiper.slides.length; i++) {
                    swiper.slides[i].style.transition = speed + "ms";
                    swiper.slides[i].querySelector(".slide-inner").style.transition = speed + "ms";
                }
            }
        }
    };

    var swiper = new Swiper(".main-slider", swiperOptions);


    // DATA BACKGROUND IMAGE
    var pageSection = $("*");
    pageSection.each(function (indx) {
        if ($(this).attr("data-background")) {
            $(this).css("background", "url(" + $(this).data("background") + ")");
        }
    });

    // DATA BACKGROUND COLOR
    var pageSection = $("*");
    pageSection.each(function (indx) {
        if ($(this).attr("data-background")) {
            $(this).css("background", $(this).data("background"));
        }
    });


})(jQuery);

window.addEventListener('load', () => {
    onMoviesFilterSelectorChange();
    onMovieAddToPlaylist();
    onRemoveMovieFromPlaylist();
    onDeletePlaylistClick();
    onSubmitRateButtonClick();
});

function onDeletePlaylistClick() {
    const confirmationModal = document.querySelector("#confirmDeleteModal");
    const openConfirmationModalBtn = document.querySelector("#openModalBtn");
    document.querySelectorAll(".delete-playlist-btn").forEach(btn => {
        btn.addEventListener('click', ev => {
            let clickedPlaylistId = btn.getAttribute("data-playlist-id");
            let clickedPlaylistName = btn.getAttribute("data-playlist-name");
            confirmationModal.querySelector("#confirmDeleteModalLabel").innerText = `Are you sure you want to delete playlist '${clickedPlaylistName}'?`;
            confirmationModal.querySelector('form').action = `${SERVER_URL}/playlists/delete/${clickedPlaylistId}`;
            openConfirmationModalBtn.click();
        });
    });
}

function onRemoveMovieFromPlaylist() {
    document.querySelectorAll('.remove-movie-from-playlist-btn').forEach(btn => {
        btn.addEventListener('click', async ev => {
            let movieId = btn.getAttribute('data-movie-id');
            let confirmRemove = confirm(`Are you sure you want to remove movie '${btn.getAttribute("data-movie-title")}' from playlist?`);
            if (confirmRemove) {


                const removeMoviePlaylistForm = document.querySelector('#remove-movie-from-playlist-form');
                let playlistId = removeMoviePlaylistForm.querySelector('#playlistId').value;
                let movieIdInput = removeMoviePlaylistForm.querySelector('#movieId');
                movieIdInput.value = movieId;

                removeMoviePlaylistForm.method = 'POST';
                removeMoviePlaylistForm.action = `${SERVER_URL}/playlists/${playlistId}/movies/remove`;
                removeMoviePlaylistForm.submit();
            }
        })
    });
}

function onMovieAddToPlaylist() {
    const addToPlaylistForm = document.querySelector('#add-to-playlist-form');
    if (addToPlaylistForm) {
        addToPlaylistForm.addEventListener('submit', (ev) => {
            let checkedList = addToPlaylistForm.querySelectorAll("input[type='checkbox']:checked")

            let playlistsIdInput = addToPlaylistForm.querySelector('#playlistsId');
            for (let i = 0; i < checkedList.length; i++) {
                playlistsIdInput.value += checkedList[i].id;
                if (i !== checkedList.length - 1) {
                    playlistsIdInput.value += ',';
                }
            }
            addToPlaylistForm.method = 'POST';
            addToPlaylistForm.action = `${SERVER_URL}/playlists/movies/add`;
        });
    }
}

function onMoviesFilterSelectorChange() {
    const sortBySelector = document.querySelector('#sortBySelect');
    const urlParams = new URLSearchParams(window.location.search);

    if (urlParams.get('sort_by')) {
        let sortByParam = urlParams.get('sort_by');
        let sortOrderParam = urlParams.get('sort_order');

        if (sortByParam === 'moviesCount') {
            if (sortOrderParam === 'desc') {
                sortBySelector.value = 'movie-count-desc';
            } else {
                sortBySelector.value = 'movie-count-asc';
            }
        } else if (sortByParam === 'name') {
            if (sortOrderParam === 'desc') {
                sortBySelector.value = 'name-desc';
            } else {
                sortBySelector.value = 'name-asc';
            }
        }
    }

    if (sortBySelector) {
        sortBySelector.addEventListener('change', (event) => {
            let sortBy, sortOrder;
            let value = sortBySelector.value;

            if (value === 'name-asc') {
                sortBy = 'name';
                sortOrder = 'asc';
            } else if (value === 'name-desc') {
                sortBy = 'name';
                sortOrder = 'desc';
            } else if (value === 'movie-count-asc') {
                sortBy = 'moviesCount';
                sortOrder = 'asc';
            } else if (value === 'movie-count-desc') {
                sortBy = 'moviesCount';
                sortOrder = 'desc';
            }

            const data = {
                sort_by: sortBy, sort_order: sortOrder
            };

            const searchParams = new URLSearchParams(data);
            window.location = '/actors?' + searchParams;
        });
    }

}

function onSubmitRateButtonClick() {
    let rateDiv = document.querySelector("#rate-div");
    if (rateDiv) {
        let rateForm = document.querySelector("#rate-form");

        rateForm.addEventListener('submit', ev => {
            if (document.querySelector('.rating').querySelector('input:checked')){
                let rate = document.querySelector('.rating').querySelector('input:checked').value;
                rateForm.querySelector("#rate-text").value = rate;
            }
        });

        rateDiv.addEventListener('click', ev => {
            document.querySelector("#openRatingModalBtn").click();
        });
    }
}