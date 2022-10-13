/* Toast ui Editor 이미지 저장후 로드 */
const { Editor } = toastui;
const editor = new Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: document.getElementById('content').value,
    previewHighlight:false,
    /* imgbb에 이미지 업로드 후 업로드된 이미지 주소 반환 */
    /* hooks 에서 addImageBlobHook를 커스텀 */
    hooks:{
        addImageBlobHook: async (blob, callback) => {
            /* blob : JS 파일 인스턴스 */
            console.log(blob);
            var formData = new FormData();
            formData.append("image", blob);
            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: '/s3/image',
                data: formData,
                mimeType: "multipart/form-data",
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function(GetImgUrl) {
                    // callback : 에디터(마크다운 편집기)에 표시할 텍스트, 뷰어에는 imageUrl 주소에 저장된 사진으로 나옴
                    // 형식 : ![대체 텍스트](주소)
                    console.log(GetImgUrl)
                    callback(GetImgUrl, '이미지');
                },
                error: function(e) {
                    callback('image_load_fail', '이미지업로드 실패');
                }
            });
        }}
});

/* submit 전에 content,walletAddress 값 hidden에 주입 및 제목 유효성 검사 */
function mdGet() { /* md = MarkDown */
    const title = document.getElementById("title").value;
    if (title == null || title.trim() === '') {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })
        Toast.fire({
            icon: 'warning',
            background:'#e76876',
            title: '<h4 style="color: white;">제목이 비어있습니다.</h4>'
        })
        return false;
    }
    document.getElementById('content').value = editor.getHTML();
    //var a = document.getElementById('content').value;
    document.getElementById('walletAddress').value = document.getElementById('wallet').textContent;
}


/* 썸네일 업로드 이벤트  */
const thumbnail = document.getElementById('uploadThumbnail');
thumbnail.addEventListener('change', uploadThumbnail);
const uploadImg = document.getElementById('uploadImg');
uploadImg.addEventListener('click', () => thumbnail.click());
/* 썸네일 삭제 */
const deleteImg = document.getElementById('deleteimg');
deleteImg.addEventListener('click', () => {
    document.getElementById('thumbnail-preview').style.display = 'none';
    document.getElementById('uploadImg').style.display = 'block';
    document.getElementById('svgicon').style.display = 'block';
    deleteImg.style.display = 'none';
    reUploadImg.style.display = 'none';
});
/* 썸네일 재 업로드 */
const reUploadImg = document.getElementById('reUploadImg');
reUploadImg.addEventListener('click', ()=> thumbnail.click());

/* 썸네일 이미지서버로 AJAX */
function uploadThumbnail(e) {
    const imgFile = e.currentTarget.files[0];
    console.log(typeof imgFile, imgFile);

    var formData = new FormData();
    formData.append("image", imgFile);
    $.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: '/s3/image',
        data: formData,
        mimeType: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function(GetImgUrl) {
            console.log(GetImgUrl)
            document.querySelector('.thumbnail-preview').setAttribute('src',GetImgUrl);
            thumbnailChange();
            document.getElementById('thumbnail').value = GetImgUrl;
        },
        error: function(e) {
            console.log(e);
        }
    });
}

/* 슬라이드 처리 Swiper.js 세팅 */
var menu = ['Slide 1', 'Slide 2']
var mySwiper = new Swiper ('.swiper-container', {
    allowTouchMove:false,
    simulateTouch:false,
    speed:500,
    /* 스크롤바 */
    scrollbar: {
        container: '.swiper-scrollbar',
        draggable: false,
    },
    /* 이동 방향 */
    navigation: {
        nextEl: '#next',
        prevEl: '#prev',
    },
    slidesPerView : 'auto',
    centeredSlides: true,
    spaceBetween: 200,
});

/* 게시글 소개글 글자수 표시 */
document.getElementById('subcontent').addEventListener('keyup', () =>{
    textAreaCheck();
});
/* 게시글 수정으로 들어올시 textArea,thumbnail 변경 */
const title = document.querySelector('title').text;
if(title === '수정') {
    textAreaCheck();
    isImgExist  = document.getElementById('thumbnail-preview').getAttribute('src');
    if(isImgExist == null || isImgExist.trim() === '') {
        thumbnailChange();
        document.getElementById('thumbnail-preview').setAttribute('src','/img/panda.png') ;
    }else {
        thumbnailChange();
    }
}
/* 썸네일 공통 로직 */
function thumbnailChange() {
    document.getElementById('svgicon').style.display = 'none';
    document.getElementById('uploadImg').style.display = 'none'; /* 업로드버튼 */
    document.getElementById('thumbnail-preview').style.display = 'block';
    deleteImg.style.display = 'block';
    reUploadImg.style.display = 'block';
}

/* 게시글 소개 글자수 체크 */
function textAreaCheck() {
    let content = document.getElementById('subcontent').value
    let textcount = document.querySelector('.textcount');
    let totaltext = document.querySelector('.totaltext');

    /* 글자수 카운트 */
    if(content.length == 0 || content == null) {
        textcount.textContent = '0';
    }else {
        textcount.textContent = content.length;
    }
    /* 글자수 제한 */
    if(content.length >= 150) {
        document.getElementById('subcontent').value = content.substring(0,150);
        textcount.textContent = '150';
        textcount.style.color = 'red';
        totaltext.style.color = 'red';
    }else{
        textcount.style.color = '#070809';
        totaltext.style.color = '#070809';
    }
}

/* 해쉬태그 입력창 */
var input = document.querySelector('input[name="tags"]');
var whitelist = ["C","C++","C#","JAVA","FrontEnd","BackEnd"];
var tagify = new Tagify(input, {
    whitelist:whitelist,
    maxTags: 10,
    dropdown: {
        maxItems: 20,
        classname: "tags-look",
        enabled: 0,
        closeOnSelect: false
    }
})


/* 메타마스크 지갑 그라데이션 텍스트 효과 */
$('.txt').html(function(i, html) {
    var chars = $.trim(html).split("");

    return '<span>' + chars.join('</span><span>') + '</span>';
});