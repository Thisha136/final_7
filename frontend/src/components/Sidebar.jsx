import "../styles/Sidebar.css";

import {

FaPlus,

FaSearch,

FaComments,

FaSignOutAlt

} from "react-icons/fa";

export default function Sidebar(){

    return(

        <div className="sidebar">

            <h2>Enterprise Assistant</h2>

            <button className="newChat">

                <FaPlus/>

                New Chat

            </button>

            <div className="search">

                <FaSearch/>

                <input placeholder="Search Chats"/>

            </div>

            <div className="history">

                <div className="item">

                    <FaComments/>

                    KYC

                </div>

                <div className="item">

                    <FaComments/>

                    Payment System

                </div>

                <div className="item">

                    <FaComments/>

                    AML

                </div>

            </div>

            <button className="logout">

                <FaSignOutAlt/>

                Logout

            </button>

        </div>

    );

}