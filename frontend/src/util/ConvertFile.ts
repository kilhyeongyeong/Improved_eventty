export const Base64toFile = (base64: string, filename: string, mimeType: string): File | null => {
    try {
        // Base64 문자열을 ArrayBuffer로 디코딩
        const byteCharacters = atob(base64);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);

        // ArrayBuffer를 Blob으로 변환
        const blob = new Blob([byteArray], {type: mimeType});

        // Blob을 File로 변환
        return new File([blob], filename, {type: mimeType});
    } catch (error) {
        console.error("Error converting base64 to File:", error);
        return null;
    }
};

export const Base64toSrc = (base64: string, filename: string) => {
    return `data:image/${filename.split(".").pop()};base64,${base64}`;
}

export const URLtoFile = async (url: string, filename: string) => {
    try {
        const response = await fetch(`${process.env["REACT_APP_NCLOUD_IMAGE_PATH"]}/${url}`);
        const blobData = await response.blob();
        const file = new File([blobData], filename, {type: blobData.type});
        console.log(file);
        return file;
    } catch (error) {
        console.error("Error while fetching the resource:", error);
        return null;
    }
}