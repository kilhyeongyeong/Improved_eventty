import React from 'react'
import {createBrowserRouter, Navigate} from 'react-router-dom';
import Login from './pages/Login';
import Main from './pages/Main';
import Signup from './pages/Signup';
import SignupMain from './pages/signup/SignupMain';
import SignupHost from './pages/signup/SignupHost';
import PublicRoute from './components/PublicRoute';
import PrivateRoute from './components/PrivateRoute';
import Error from './pages/Error';
import Events from "./pages/Events";
import Layout from "./components/display/Layout";
import RootSetStates from "./components/RootSetStates";
import User from "./pages/User";
import EventDetail from "./pages/events/EventDetail";
import Profile from "./pages/user/Profile";
import Registers from "./pages/user/Registers";
import Write from "./pages/Write";
import SignupUser from "./pages/signup/SignupUser";
import {loader as eventLoader} from "./routes/event";
import {loader as eventListLoader} from "./routes/events";
import {loader as profileLoader} from "./routes/profile";
import {loader as categoryLoader} from "./routes/category";
import {loader as searchLoader} from "./routes/search";
import {loader as registerLoader} from "./routes/register";
import {loader as updateLoader} from "./routes/update";
import {loader as applyLoader} from "./routes/apply";
import {loader as applicesLoader} from "./routes/applices";
import HostRoute from "./components/HostRoute";
import Bookings from "./pages/user/Bookings";
import EventBooking from "./pages/events/EventBooking";
import EventsList from "./pages/events/EventsList";
import EventsError from "./exception/EventsError";
import Find from "./pages/Find";
import FindResult from "./pages/find/FindResult";
import Update from "./pages/Update";
import UserRoute from "./components/UserRoute";
import NaverLogin from "./pages/login/NaverLogin";
import Applices from "./pages/user/Applices";
import WebApplicesResult from "./components/user/web/applices/WebApplicesResult";
import ApplicesResult from "./pages/user/applices/ApplicesResult";

const Router = createBrowserRouter([
        {
            path: "",
            element: (
                <RootSetStates/>
            ),
            errorElement: <Error/>,
            children: [
                {
                    element: <Layout/>,
                    children: [
                        {
                            path: "",
                            element: <Main/>,
                        },
                        {
                            path: "events/*",
                            element: <Events/>,
                            children: [
                                {
                                    path: "",
                                    element: <EventsList/>,
                                    loader: eventListLoader,
                                },
                                {
                                    path: "category/:category",
                                    element: <EventsList/>,
                                    loader: categoryLoader,
                                    errorElement: <EventsError/>,
                                },
                                {
                                    path: "search",
                                    id: "search",
                                    element: <EventsList/>,
                                    loader: searchLoader,
                                    errorElement: <EventsError/>,
                                }
                            ]
                        },
                        {
                            path: "event/*",
                            id: "event",
                            loader: eventLoader,
                            children: [
                                {
                                    path: ":eventId",
                                    element: <EventDetail/>,
                                },
                                {
                                    element: <PrivateRoute/>,
                                    children: [
                                        {
                                            element: <UserRoute/>,
                                            children: [
                                                {
                                                    path: ":eventId/booking",
                                                    element: <EventBooking/>,
                                                    loader: profileLoader,
                                                },
                                            ]
                                        }
                                    ]
                                },
                            ]
                        },

                        {
                            path: "users/*",
                            element: <User/>,
                            children: [
                                {
                                    path: "profile",
                                    id: "profile",
                                    element: <Profile/>,
                                    loader: profileLoader,
                                    errorElement: <Navigate to={"/login"}/>,
                                },
                                {
                                    element: <HostRoute/>,
                                    children: [
                                        {
                                            path: "events",
                                            element: <Registers/>,
                                            loader: registerLoader,
                                            errorElement: <EventsError/>,
                                        },
                                    ]
                                },
                                {
                                    element: <UserRoute/>,
                                    children: [
                                        {
                                            path: "bookings",
                                            element: <Bookings/>,
                                            loader: applyLoader,
                                            errorElement: <EventsError/>,
                                        }
                                    ]
                                },
                            ]
                        },
                        {
                            element: <HostRoute/>,
                            children: [
                                {
                                    path: "users/events/applices",
                                    element: <Applices/>,
                                    children: [
                                        {
                                            path: ":type/:eventId",
                                            element: <ApplicesResult/>,
                                            errorElement: <EventsError/>,
                                        },
                                    ]
                                }
                            ]
                        }
                    ],
                },
                {
                    element: <PublicRoute/>,
                    children: [
                        {
                            path: "/login",
                            element: <Login/>,
                        },
                        {
                            path: "/signup",
                            element: <Signup/>,
                            children: [
                                {
                                    path: "",
                                    element: <SignupMain/>
                                },
                                {
                                    path: "user",
                                    element: <SignupUser/>
                                },
                                {
                                    path: "host",
                                    element: <SignupHost/>
                                },
                            ]
                        },
                        {
                            path: "/find/:params",
                            element: <Find/>,
                        },
                        {
                            path: "/find/result/:params",
                            element: <FindResult/>,
                        },
                        {
                            path: "/naver/*",
                            element: <NaverLogin/>,
                        }
                    ]
                },
                {
                    element: <PrivateRoute/>,
                    children: [
                        {
                            element: <HostRoute/>,
                            children: [
                                {
                                    path: "write",
                                    element: <Write/>,
                                },
                                {
                                    path: "update/:eventId",
                                    element: <Update/>,
                                    loader: updateLoader,
                                },
                            ]
                        },
                    ]
                },
            ],
        },
    ])
;

export default Router;