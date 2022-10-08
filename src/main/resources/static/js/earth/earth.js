window.onload=function(){
    console.log(dotList)
    tippy("#aaa", {
        content: "AAA님이 구매했습니다.",
        theme: 'custom',
        arrow: true,
    });
    const rect_Collection = document.querySelectorAll('rect');
    /* 도트 */
    for(let i=0; i<rect_Collection.length; i++) {
        /* 아이디 부여 */
        rect_Collection[i].setAttribute('id','dot'+i.toString());

        /* 가격 부여 */
        if(0 <= i <= 215 ) {
            rect_Collection[i].setAttribute('price','500모각코인');
        }
        if(216 <= i <= 666) {
            rect_Collection[i].setAttribute('price','1500모각코인');
        }
        if(667 <= i <= 1660) {
            rect_Collection[i].setAttribute('price','2000모각코인');
        }
        if(1661 <= i <= 2370) {
            rect_Collection[i].setAttribute('price','1500모각코인');
        }
        if(2371 <= i <= 2619) {
            rect_Collection[i].setAttribute('price','500모각코인');
        }

        /* 클릭 이벤트 부여 */
        rect_Collection[i].addEventListener('click', () => {
            $('#exampleModal').modal('show');
            document.getElementById('price').textContent = rect_Collection[i].getAttribute('price');
        });
    }


}