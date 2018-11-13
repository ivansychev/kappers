function div(val, by){
    return (val - val % by) / by;
}

var http = {
    status : {
        isOk : function(httpStatus) {
            if (Number.isInteger(httpStatus) && div(httpStatus, 200) == 1) {
                return true;
            }
            return false;
        }
    },
    response : {
        defaultTransform : function (data, headers, status) {
            var jsonHeaders = headers ? angular.fromJson(headers) : undefined;
            var jsonBody = data ? angular.fromJson(data) : {};
            return {headers : jsonHeaders, body: jsonBody, status: status};
        },
        defaultResolve : function (result, onSuccess, onFailure) {
            if (result.hasOwnProperty('errorCode') || !http.status.isOk(result.status)) {
                onFailure(result.body);
            } else {
                onSuccess(result.body);
            }
        }
    }
}

const STATE = {
    ACTIVE : 'ACTIVE'
    , DELETED : 'DELETED'
}