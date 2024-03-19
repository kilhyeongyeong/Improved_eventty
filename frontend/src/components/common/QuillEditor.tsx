import React from "react";
import 'react-quill/dist/quill.snow.css';
import ReactQuill from "react-quill";

interface IQuill {
    value: string;
    onChange: () => void;
    onBlur: () => void;
    inputRef: React.Ref<any>;
}

const toolbarOptions = [
    [{ header: [1, 2, 3, false] }],
    ["bold", "italic", "underline", "strike"],
    ["blockquote"],
    [{ list: "ordered" }, { list: "bullet" }],
    [{ color: [] }, { background: [] }],
    [{ align: [] }],
    ["link", "video"],
];

const formats = [
    "header",
    "font",
    "size",
    "bold",
    "italic",
    "underline",
    "strike",
    "align",
    "blockquote",
    "list",
    "bullet",
    "indent",
    "background",
    "color",
    "link",
    "image",
    "video",
    "width",
];

const modules = {
    toolbar: {
        container: toolbarOptions,
    },
};

function QuillEditor({value, onChange, onBlur, inputRef}: IQuill) {
    return (
        <>
            <ReactQuill
                value={value || ""}
                onChange={onChange}
                onBlur={onBlur}
                ref={inputRef}
                theme={"snow"}
                modules={modules}
                formats={formats}
                style={{height: "500px", flex:1, fontSize: "20px"}}
            />
        </>
    );
}

export default QuillEditor;