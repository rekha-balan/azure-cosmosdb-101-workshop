module.exports = function (context, documents) {
    
    if (!!documents && documents.length > 0) {
        var count = documents.length;
        context.log('documents count: ' + count);
        for (var i = 0; i < count; i++) {
            var doc = documents[i];
            if (doc['city_name'].includes('ville')) {
                context.bindings.outputDocument = doc;
                context.log('doc saved: ', doc);
            }
        }        
    }
    context.done();
}
