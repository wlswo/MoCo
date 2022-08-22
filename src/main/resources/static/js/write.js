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

/* submit 버튼클릭시 썸네일, content hidden에 부여 */
function mdGet() {
    document.getElementById('content').value = editor.getHTML();
    var a = document.getElementById('content').value;
    const regex = /<img.*?src=['"](.*?)['"]/;
    var thumbnail = regex.exec(a);
    document.getElementById('thumbnail').value = thumbnail[1];
}