window.onload=function(){
    console.log(dotList[0]);
    const rect_Collection = document.querySelectorAll('rect');
    /* 도트 */
    outerFor : for(let i=0; i<rect_Collection.length; i++) {
        /* 아이디 부여 */
        rect_Collection[i].setAttribute('id','dot'+i.toString());

        /* 가격 부여 */
        if(0 <= i && i <= 215 ) {
            rect_Collection[i].setAttribute('price','500');
        }
        else if(216 <= i && i <= 666) {
            rect_Collection[i].setAttribute('price','1500');
        }
        else if(667 <= i && i <= 1660) {
            rect_Collection[i].setAttribute('price','2000');
        }
        else if(1661 <= i && i <= 2370) {
            rect_Collection[i].setAttribute('price','1500');
        }
        else {
            rect_Collection[i].setAttribute('price','500');
        }

        /* 구매된 도트 처리 */
        for (let j = 0; j < dotList.length; j++) {
            if('dot'+i.toString()  === dotList[j].dotId.toString()) {
                let {dotId,userName,description,color,txHash,createdDate,picture} = dotList[j];
                const dot = document.getElementById(dotId);
                dot.style.fill = color;
                createdDate = createdDate.replace('T',' ');
                dot.addEventListener('click',()=>{
                    $('#purchasedCardModal').modal('show');
                    document.getElementById('dotPicture').src = picture;
                    document.getElementById('buyer').textContent = userName;
                    document.getElementById('dotDescription').textContent = description;
                    document.getElementById('createDate').textContent = createdDate;
                    document.getElementById('dotTxHash').href = 'https://goerli.etherscan.io/tx/' + txHash;
                })
                tippy('#'+dotId, {
                    content: userName + '님이 구매했습니다.',
                    theme: 'purchased',
                    arrow: true,
                });
                dotList.splice(j,1);
                continue outerFor;
            }
        }

        /* 구매가능 지역 툴팁 표시 */
        tippy("#dot"+i.toString(), {
            content: rect_Collection[i].getAttribute('price') + '토큰에 구매할수 있는 지역입니다.',
            theme: 'notPurchase',
            arrow: false,
        });

        /* 도트 클릭시 모달창 생성 */
        rect_Collection[i].addEventListener('click', () => {
            $('#buyDotModal').modal('show');
            if(document.getElementById('buydot-wallet')){
                document.getElementById('dotId').value = rect_Collection[i].getAttribute('id');
                document.getElementById('price').textContent = '가격 : ' + rect_Collection[i].getAttribute('price') + '토큰입니다.';
            }
        });
    }
}
