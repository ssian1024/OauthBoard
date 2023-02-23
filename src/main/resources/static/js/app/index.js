var index = {
    // post-save 페이지 등록 설정 및 Validation 연동
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
            if(confirm("등록하시겠습니까?")){
                let userFormData = data();
                setUsers(userFormData);
            }
        })
        //JSON 정보 가져옴
        function data(){
            return {
                title: $('#title').val(),
                author: $('#author').val(),
                content: $('#content').val()
            }
        }
        function setUsers(data) {
            $.ajax({
                type: 'POST',
                url: '/posts/save',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function (response) {
                if (response['saveResult'] === 200) {
                    alert('정상적으로 등록되었습니다.');
                    window.location.href = '/';
                } else {
                    console.log(response)
                    setFormValidationMessage(response);
                }
            })
        }

        _this.save = function () {
            $('#titleValidMessage').html("");
            $('#authorValidMessage').html("");
            $('#contentValidMessage').html("");
        }


        function setFormValidationMessage(validationMessage) {
            $.each(validationMessage, function (index, item) {
                $('#' + index + "ValidMessage").html(item);
            })
        }
        $('#cancelButton').on("click", function () {
            if (confirm("사용자 목록 페이지로 이동하시겠습니까?" + '\n' + "이동 시 입력 내용은 저장되지 않습니다.")) {
                window.location.href = '/users/list';
            }
        })


        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },

    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

index.init();

