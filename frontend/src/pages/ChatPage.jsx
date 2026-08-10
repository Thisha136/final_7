import { useState } from "react";
import {
    FaPlus,
    FaSearch,
    FaCloudUploadAlt,
    FaPaperPlane,
    FaUserCircle,
    FaBars,
    FaTimes,
    FaFilePdf,
    FaChevronDown
} from "react-icons/fa";

import "../styles/ChatPage.css";
import ShareButton from "../components/ShareButton";
export default function ChatPage() {

    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [question, setQuestion] = useState("");

    const conversations = {
        today: [
            "KYC Guidelines",
            "Payment System",
            "RBI Circular",
        ],

        yesterday: [
            "AML Documents",
            "Customer Verification",
            "Payment Participants",
        ]
    };

    const sendMessage = () => {

        if (!question.trim()) {
            return;
        }

        console.log("Question:", question);

        setQuestion("");
    };

    const handleKeyDown = (e) => {

        if (e.key === "Enter" && !e.shiftKey) {

            e.preventDefault();

            sendMessage();
        }
    };

    return (

        <div className="chat-app">

            {/* MOBILE OVERLAY */}

            {sidebarOpen && (
                <div
                    className="sidebar-overlay"
                    onClick={() => setSidebarOpen(false)}
                />
            )}


            {/* ================= SIDEBAR ================= */}

            <aside
                className={`chat-sidebar ${
                    sidebarOpen ? "open" : ""
                }`}
            >

                {/* SIDEBAR HEADER */}

                <div className="sidebar-header">

                    <div className="brand">

                        <div className="brand-icon">
                            R
                        </div>

                        <div>

                            <h2>
                                Enterprise RAG
                            </h2>

                            <span>
                                Knowledge Assistant
                            </span>

                        </div>

                    </div>

                    <button
                        className="close-sidebar"
                        onClick={() => setSidebarOpen(false)}
                    >
                        <FaTimes />
                    </button>

                </div>


                {/* NEW CHAT */}

                <button className="new-chat-button">

                    <FaPlus />

                    <span>
                        New Chat
                    </span>

                </button>


                {/* SEARCH */}

                <div className="conversation-search">

                    <FaSearch />

                    <input
                        type="text"
                        placeholder="Search conversations..."
                    />

                </div>


                {/* CHAT HISTORY */}

                <div className="conversation-list">

                    {/* TODAY */}

                    <div className="history-section">

                        <div className="history-title">

                            <span>
                                Today
                            </span>

                            <FaChevronDown />

                        </div>


                        {conversations.today.map(
                            (chat, index) => (

                                <button
                                    className={`conversation ${
                                        index === 0
                                            ? "selected"
                                            : ""
                                    }`}
                                    key={chat}
                                >

                                    <FaFilePdf />

                                    <span>
                                        {chat}
                                    </span>

                                </button>

                            )
                        )}

                    </div>


                    {/* YESTERDAY */}

                    <div className="history-section">

                        <div className="history-title">

                            <span>
                                Yesterday
                            </span>

                            <FaChevronDown />

                        </div>


                        {conversations.yesterday.map(
                            (chat) => (

                                <button
                                    className="conversation"
                                    key={chat}
                                >

                                    <FaFilePdf />

                                    <span>
                                        {chat}
                                    </span>

                                </button>

                            )
                        )}

                    </div>

                </div>


                {/* SIDEBAR FOOTER */}

                <div className="sidebar-footer">

                    <div className="user-profile">

                        <FaUserCircle />

                        <div>

                            <strong>
                                User
                            </strong>

                            <span>
                                Enterprise Account
                            </span>

                        </div>

                    </div>


                    <button className="logout-button">
                        Logout
                    </button>

                </div>

            </aside>


            {/* ================= MAIN ================= */}

            <main className="chat-main">


                {/* TOP BAR */}

                <header className="chat-topbar">

                    <button
                        className="mobile-menu"
                        onClick={() =>
                            setSidebarOpen(true)
                        }
                    >
                        <FaBars />
                    </button>


                    <div className="topbar-title">

                        <h1>
                            Enterprise Knowledge Assistant
                        </h1>

                        <span>
                            AI-powered document search
                        </span>

                    </div>


                    <div className="topbar-user">

                        <span>
                            Enterprise
                        </span>

                        <FaUserCircle />

                    </div>

                </header>


                {/* CHAT CONTENT */}

                <section className="chat-content">


                    {/* WELCOME */}

                    <div className="welcome-section">

                        <div className="welcome-icon">
                            ✦
                        </div>

                        <h1>
                            How can I help you today?
                        </h1>

                        <p>
                            Ask questions about your uploaded
                            enterprise documents and get accurate
                            answers with sources.
                        </p>

                    </div>


                    {/* UPLOAD */}

                    <div className="upload-card">

                        <div className="upload-icon">

                            <FaCloudUploadAlt />

                        </div>


                        <div className="upload-content">

                            <h3>
                                Upload your documents
                            </h3>

                            <p>
                                Upload PDF documents to search
                                and chat with your enterprise
                                knowledge base.
                            </p>

                            <span className="file-types">
                                PDF • DOCX • TXT
                            </span>

                        </div>


                        <button className="upload-button">

                            <FaCloudUploadAlt />

                            <span>
                                Upload Files
                            </span>

                        </button>

                    </div>


                    {/* SUGGESTIONS */}

                    <div className="suggestion-section">

                        <h3>
                            Try asking
                        </h3>


                        <div className="suggestions">

                            <button
                                onClick={() =>
                                    setQuestion(
                                        "Explain the KYC guidelines"
                                    )
                                }
                            >

                                <span>
                                    📋
                                </span>

                                <div>
                                    Explain the KYC guidelines
                                </div>

                            </button>


                            <button
                                onClick={() =>
                                    setQuestion(
                                        "What is the Payment System?"
                                    )
                                }
                            >

                                <span>
                                    💳
                                </span>

                                <div>
                                    What is the Payment System?
                                </div>

                            </button>


                            <button
                                onClick={() =>
                                    setQuestion(
                                        "Explain Section 18"
                                    )
                                }
                            >

                                <span>
                                    📖
                                </span>

                                <div>
                                    Explain Section 18
                                </div>

                            </button>


                            <button
                                onClick={() =>
                                    setQuestion(
                                        "Who are the participants?"
                                    )
                                }
                            >

                                <span>
                                    👥
                                </span>

                                <div>
                                    Who are the participants?
                                </div>

                            </button>

                        </div>

                    </div>

                </section>


                {/* ================= INPUT ================= */}

                <div className="chat-input-wrapper">

                    <div className="chat-input-box">

                        <textarea
                            value={question}
                            onChange={(e) =>
                                setQuestion(
                                    e.target.value
                                )
                            }
                            onKeyDown={handleKeyDown}
                            placeholder="Ask anything about your documents..."
                            rows="1"
                        />


                        <button
                            className={`send-button ${
                                question.trim()
                                    ? "active"
                                    : ""
                            }`}
                            onClick={sendMessage}
                        >

                            <FaPaperPlane />

                        </button>

                    </div>


                    <p className="input-hint">

                        Press Enter to send •
                        Shift + Enter for a new line

                    </p>

                </div>

            </main>

        </div>
    );
}