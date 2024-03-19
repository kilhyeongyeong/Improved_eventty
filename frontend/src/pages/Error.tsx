import {Button, Container, Stack, Title} from '@mantine/core';
import {Link} from 'react-router-dom';
import customStyle from "../styles/customStyle";

export function Error() {
    const {classes} = customStyle();

    return (
        <Container>
            <Stack align={"center"} style={{marginTop: "20vh"}}>
                <img src={`${process.env.PUBLIC_URL}/images/404.png`}/>
                <Title order={3}>요청하신 페이지를 찾을 수 없습니다</Title>
                <Button component={Link} to={"/"}
                        className={classes["btn-primary-outline"]}
                        style={{width: "10rem"}}>
                    홈으로 이동
                </Button>
            </Stack>
        </Container>
    );
}

export default Error;