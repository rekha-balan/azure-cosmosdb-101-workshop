module.exports = function (context, req) {

    if (req.body) {
        context.log("ZipCodeHttpTriggerJS1 - POST, body: ", req.body);
        var body = req.body;
        var postal_cd  = body['postal_cd'];
        context.log("postal_cd: " + postal_cd);
        body['partition_key'] = postal_cd;

        context.bindings.outputDocument = body;
        context.log("ZipCodeHttpTriggerJS1 - doc: ", body);

        context.res = {
            status: 201,
            body: "Accepted"
        };
    }
    else {
        context.log("ZipCodeHttpTriggerJS1 - GET, ignored");
        context.res = {
            status: 400,
            body: "Only POST requests accepted"
        };
    }

    context.done();
};
