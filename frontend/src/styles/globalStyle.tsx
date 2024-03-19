import { createGlobalStyle } from "styled-components";

const globalStyle = createGlobalStyle`
  @font-face {
    font-family: "NanumSquareNeo-Variable";
    font-display: swap;
    src: local("NanumSquareNeo-Variable"),
    url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.woff2`}) format("woff2"),
    url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.woff`}) format("woff"),
    url(${`${process.env.PUBLIC_URL}/fonts/NanumSquareNeo-Variable.eot?#iefix`}) format("embedded-opentype");
  }

  * {
    font-family: "NanumSquareNeo-Variable" !important;
    text-decoration: none;
    color: inherit;
  }

  :root {
    //--primary: #7536DC;
    --primary: #4E52DC;
    --primary-rgb: 78, 82, 220;
    --primary-light: rgba(var(--primary-rgb), 0.1);
  }

  .ql-editor{
    height: 500px !important;
    overflow: hidden;
    overflow-y: scroll;
    overflow-x: scroll;
  }
`;

export default globalStyle;