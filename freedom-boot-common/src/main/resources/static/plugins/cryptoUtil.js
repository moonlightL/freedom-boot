var Crypto = {
    AES: {
        enc: function(originalText) {
            var key = CryptoJS.enc.Utf8.parse("bWFsbHB3ZA==WNST");
            var srcs = CryptoJS.enc.Utf8.parse(originalText);
            var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
            return encrypted.toString();
        },
        dec: function (data) {
            var key = CryptoJS.enc.Utf8.parse("bWFsbHB3ZA==WNST");
            var decrypt = CryptoJS.AES.decrypt(data, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
            return CryptoJS.enc.Utf8.stringify(decrypt).toString();
        }
    },
    DES: {
        enc: function() {

        },
        dec: function () {

        }
    }
}