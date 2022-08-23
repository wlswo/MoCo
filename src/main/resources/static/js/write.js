/* Toast ui Editor 이미지 저장후 로드 */
const { Editor } = toastui;
const editor = new Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '500px',
    theme: 'dark',
    previewHighlight:false,
    // hooks 에서 addImageBlobHook를 커스텀
    hooks:{
        addImageBlobHook: async (blob, callback) => {
            /* blob : JS 파일 인스턴스 */
            console.log(blob);
            var form = new FormData();
            form.append("image", blob);
            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: 'https://api.imgbb.com/1/upload?key=add794e5ba66a94a09b9661bb2884722',
                data: form,
                mimeType: "multipart/form-data",
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function(data) {
                    // callback : 에디터(마크다운 편집기)에 표시할 텍스트, 뷰어에는 imageUrl 주소에 저장된 사진으로 나옴
                    // 형식 : ![대체 텍스트](주소)
                    displayUrl = JSON.parse(data).data.display_url;
                    console.log(displayUrl)
                    callback(displayUrl, '사진');
                },
                error: function(e) {
                    callback('image_load_fail', '이미지업로드 실패');
                }
            });
        }}
});

/* submit 전에 content 값 hidden에 주입 */
function mdGet() {
    document.getElementById('content').value = editor.getHTML();
    var a = document.getElementById('content').value;
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
document.querySelector('a').style.display = 'none';
/* 썸네일 이미지서버와 AJAX */
function uploadThumbnail(e) {
    const files = e.currentTarget.files;
    console.log(typeof files, files);
    if([...files] >= 2) {
        return;
    }
    [...files].forEach(file => {
        if(!file.type.match("image/.*")) {
            alert("이미지만 업로드 할수있습니다.");
            return;
        }
    })
    var form = new FormData();
    form.append("image", files[0]);
    /* ajax */
    $.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'https://api.imgbb.com/1/upload?key=add794e5ba66a94a09b9661bb2884722',
        data: form,
        mimeType: "multipart/form-data",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function(data) {
            displayUrl = JSON.parse(data).data.display_url;
            console.log(displayUrl);
            document.getElementById('svgicon').style.display = 'none';
            document.getElementById('thumbnail-preview').style.display = 'block';
            document.querySelector('.thumbnail-preview').setAttribute('src',displayUrl);
            deleteImg.style.display = 'block';
            reUploadImg.style.display = 'block';
            document.getElementById('uploadImg').style.display = 'none';
        },
        error: function(e) {
            console.log(e);
        }
    });
}

/* 슬라이드 처리 */
var menu = ['Slide 1', 'Slide 2']
var mySwiper = new Swiper ('.swiper-container', {
    allowTouchMove:false,
    speed:500,
    // Navigation arrows
    navigation: {
        nextEl: '#next',
        prevEl: '#prev',
    },
});