function likeCheck(e){const t=document.getElementsByName("isClick").item(0).value;console.log(t),"noclick"===t?(httpRequest=new XMLHttpRequest,httpRequest.open("POST","/like/"+e),httpRequest.responseType="json",httpRequest.send(),httpRequest.onreadystatechange=()=>{if(httpRequest.readyState===XMLHttpRequest.DONE)if(200===httpRequest.status)document.getElementsByName("isClick").item(0).value="clicked",document.getElementsByClassName("new").item(0).textContent++,document.getElementsByClassName("current").item(0).textContent++;else{let e=httpRequest.response;console.log(e.message)}}):(httpRequest=new XMLHttpRequest,httpRequest.open("DELETE","/like/"+e),httpRequest.responseType="json",httpRequest.send(),httpRequest.onreadystatechange=()=>{if(httpRequest.readyState===XMLHttpRequest.DONE)if(200===httpRequest.status)document.getElementsByName("isClick").item(0).value="noclick",document.getElementsByClassName("new").item(0).textContent--,document.getElementsByClassName("current").item(0).textContent--;else{let e=httpRequest.response;console.log(e.message)}})}function recruit(e){const t=document.getElementById("boardId").value;$.ajax({url:"/board/recruit/"+t+"/"+e,type:"POST",success:function(e){notification("참가신청완료!"),document.getElementById("currentJoin").textContent++},error:function(e){notification("이미 참가 되어있는 모집입니다.")}})}function recruitCancel(e){const t=document.getElementById("boardId").value;$.ajax({url:"/board/recruit-cancel/"+t+"/"+e,type:"DELETE",success:function(e){notification("참가취소완료!"),document.getElementById("currentJoin").textContent--},error:function(e){notification("참가상태가 아닙니다.")}})}async function recruitClose(e){const t=document.getElementById("recruitCloseButton").textContent;(await confirm("","정말로 "+t+" 하시겠습니까?")).isConfirmed&&$.ajax({url:"/board/recruitClose/"+e,type:"PATCH",success:function(e){e?(SuccessAlert("모집마감되었습니다."),document.getElementById("recruitCloseButton").innerText="마감취소",document.getElementById("recruitCloseButton").style.backgroundColor="#94324b"):(notification("모집마감취소."),document.getElementById("recruitCloseButton").innerText="마감하기",document.getElementById("recruitCloseButton").style.backgroundColor="#74d65c")},error:function(e){notification("다시 시도해주세요.")}})}function saveComment(e){const t=document.getElementById("boardId").value;let n;var o;if(void 0===e||null==e||""==e?(n=document.getElementById("comment").value,o=!1):(n=document.getElementById("recomment-content-"+e).value,o=!0),!n||""===n.trim())return alert("공백 또는 빈 문자열은 입력하실수 없습니다."),!1;httpRequest=new XMLHttpRequest;var s=new Object;s.comment=n,s.boardId=t,o?httpRequest.open("POST","/comment/"+t+"/"+e):httpRequest.open("POST","/comment/"+t),httpRequest.setRequestHeader("Content-Type","application/json"),httpRequest.responseType="json",httpRequest.send(JSON.stringify(s)),httpRequest.onreadystatechange=()=>{httpRequest.readyState===XMLHttpRequest.DONE&&(200===httpRequest.status?o?($("#ul-"+e).load(location.href+" #ul-"+e+" li"),$("#recomment-content-"+e).val("")):($("#commentList").load(location.href+" #commentList"),$("#comment").val("")):console.log(httpRequest.response))}}function commentDelete(e,t){confirm("정말 삭제하시겠습니까?")&&(httpRequest=new XMLHttpRequest,httpRequest.open("DELETE","/admin/comment/"+e),httpRequest.send(),httpRequest.onreadystatechange=()=>{if(httpRequest.readyState===XMLHttpRequest.DONE)if(200===httpRequest.status)if("comment"===t)$("#commentList").load(location.href+" #commentList");else{const t=$("#multi-collapse-"+e).closest("ul").attr("id");$("#"+t).load(location.href+" #"+t+" li")}else{let e=httpRequest.response;console.log(e.message)}})}