import { useState } from "react";
import {
    FaBars,
    FaTimes,
    FaTachometerAlt,
    FaFileAlt,
    FaUsers,
    FaComments,
    FaSignOutAlt,
    FaCloudUploadAlt,
    FaDatabase,
    FaChevronRight,
    FaUserCircle,
    FaExclamationTriangle,
    FaCheckCircle,
    FaChartLine,
    FaSearch,
    FaPlus,
    FaRobot,
    FaShieldAlt
} from "react-icons/fa";

import { useNavigate } from "react-router-dom";
import "../styles/AdminPage.css";

export default function AdminPage() {

    const navigate = useNavigate();

    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [search, setSearch] = useState("");

    const documents = [
        {
            name: "ds4-cir-kyc.pdf",
            type: "PDF",
            size: "2.4 MB",
            uploaded: "Today",
            status: "Indexed"
        },
        {
            name: "RBI-Payment-System.pdf",
            type: "PDF",
            size: "4.1 MB",
            uploaded: "Yesterday",
            status: "Indexed"
        },
        {
            name: "AML-Guidelines.docx",
            type: "DOCX",
            size: "1.8 MB",
            uploaded: "2 days ago",
            status: "Indexed"
        },
        {
            name: "Customer-Verification.txt",
            type: "TXT",
            size: "540 KB",
            uploaded: "3 days ago",
            status: "Indexed"
        }
    ];

    const filteredDocuments = documents.filter((doc) =>
        doc.name.toLowerCase().includes(search.toLowerCase())
    );

    const logout = () => {
        localStorage.removeItem("isLoggedIn");
        navigate("/");
    };

    const goToConflicts = () => {
        navigate("/conflicts");
    };

    return (
        <div className="admin-app">

            {/* MOBILE OVERLAY */}

            {sidebarOpen && (
                <div
                    className="admin-overlay"
                    onClick={() => setSidebarOpen(false)}
                />
            )}

            {/* ================= SIDEBAR ================= */}

            <aside
                className={`admin-sidebar ${
                    sidebarOpen ? "open" : ""
                }`}
            >

                <div className="admin-brand">

                    <div className="admin-logo">
                        R
                    </div>

                    <div>
                        <h2>Enterprise RAG</h2>
                        <span>Admin Console</span>
                    </div>

                    <button
                        className="admin-close"
                        onClick={() => setSidebarOpen(false)}
                    >
                        <FaTimes />
                    </button>

                </div>


                {/* MAIN NAVIGATION */}

                <div className="nav-label">
                    MAIN MENU
                </div>

                <nav className="admin-nav">

                    <button className="admin-nav-item active">
                        <FaTachometerAlt />
                        <span>Dashboard</span>
                    </button>

                    <button className="admin-nav-item">
                        <FaFileAlt />
                        <span>Documents</span>
                    </button>

                    <button className="admin-nav-item">
                        <FaUsers />
                        <span>Users</span>
                    </button>

                    <button className="admin-nav-item">
                        <FaComments />
                        <span>Conversations</span>
                    </button>

                    <button
                        className="admin-nav-item"
                        onClick={goToConflicts}
                    >
                        <FaExclamationTriangle />
                        <span>Conflicts</span>
                        <small>4</small>
                    </button>

                </nav>


                {/* AI SECTION */}

                <div className="nav-label">
                    AI SYSTEM
                </div>

                <div className="ai-status">

                    <div className="ai-icon">
                        <FaRobot />
                    </div>

                    <div>
                        <strong>RAG Engine</strong>
                        <span>
                            Online and ready
                        </span>
                    </div>

                    <div className="online-dot"></div>

                </div>


                {/* SIDEBAR BOTTOM */}

                <div className="admin-sidebar-bottom">

                    <div className="admin-user">

                        <FaUserCircle />

                        <div>
                            <strong>Administrator</strong>
                            <span>Admin Account</span>
                        </div>

                    </div>

                    <button
                        className="logout-button"
                        onClick={logout}
                    >
                        <FaSignOutAlt />
                        Logout
                    </button>

                </div>

            </aside>


            {/* ================= MAIN ================= */}

            <main className="admin-main">

                {/* TOPBAR */}

                <header className="admin-topbar">

                    <button
                        className="mobile-menu"
                        onClick={() =>
                            setSidebarOpen(true)
                        }
                    >
                        <FaBars />
                    </button>

                    <div className="topbar-heading">
                        <h1>Dashboard</h1>

                        <p>
                            Enterprise RAG management overview
                        </p>
                    </div>

                    <div className="topbar-right">

                        <div className="system-online">
                            <span></span>
                            System Online
                        </div>

                        <FaUserCircle />

                    </div>

                </header>


                {/* ================= CONTENT ================= */}

                <section className="admin-content">

                    {/* WELCOME */}

                    <div className="welcome-banner">

                        <div className="welcome-text">

                            <span className="welcome-badge">
                                <FaShieldAlt />
                                Enterprise Workspace
                            </span>

                            <h2>
                                Welcome back, Administrator 👋
                            </h2>

                            <p>
                                Manage your documents, users,
                                conversations and AI knowledge
                                system from one place.
                            </p>

                        </div>

                        <button
                            className="upload-button"
                        >
                            <FaCloudUploadAlt />
                            Upload Document
                        </button>

                    </div>


                    {/* ================= STATISTICS ================= */}

                    <div className="stats-grid">

                        <div className="stat-card">

                            <div className="stat-card-top">

                                <div className="stat-icon purple">
                                    <FaFileAlt />
                                </div>

                                <span className="stat-growth">
                                    +16.7%
                                </span>

                            </div>

                            <p>Total Documents</p>

                            <h3>24</h3>

                            <span className="stat-description">
                                4 added this month
                            </span>

                        </div>


                        <div className="stat-card">

                            <div className="stat-card-top">

                                <div className="stat-icon blue">
                                    <FaUsers />
                                </div>

                                <span className="stat-growth">
                                    +20%
                                </span>

                            </div>

                            <p>Total Users</p>

                            <h3>18</h3>

                            <span className="stat-description">
                                3 new this week
                            </span>

                        </div>


                        <div className="stat-card">

                            <div className="stat-card-top">

                                <div className="stat-icon green">
                                    <FaComments />
                                </div>

                                <span className="stat-growth">
                                    +15.5%
                                </span>

                            </div>

                            <p>Conversations</p>

                            <h3>156</h3>

                            <span className="stat-description">
                                21 conversations today
                            </span>

                        </div>


                        <div className="stat-card">

                            <div className="stat-card-top">

                                <div className="stat-icon orange">
                                    <FaDatabase />
                                </div>

                                <span className="stat-growth">
                                    Healthy
                                </span>

                            </div>

                            <p>Indexed Data</p>

                            <h3>1.8 GB</h3>

                            <span className="stat-description">
                                Vector database
                            </span>

                        </div>

                    </div>


                    {/* ================= MAIN GRID ================= */}

                    <div className="dashboard-grid">

                        {/* DOCUMENTS */}

                        <div className="dashboard-card documents-card">

                            <div className="card-header">

                                <div>
                                    <h3>
                                        Recent Documents
                                    </h3>

                                    <p>
                                        Latest files added to
                                        knowledge base
                                    </p>
                                </div>

                                <button className="view-button">
                                    View All
                                    <FaChevronRight />
                                </button>

                            </div>


                            {/* SEARCH */}

                            <div className="document-search">

                                <FaSearch />

                                <input
                                    type="text"
                                    placeholder="Search documents..."
                                    value={search}
                                    onChange={(e) =>
                                        setSearch(e.target.value)
                                    }
                                />

                            </div>


                            {/* DOCUMENT LIST */}

                            <div className="document-list">

                                {filteredDocuments.length === 0 ? (

                                    <div className="empty-result">
                                        No documents found.
                                    </div>

                                ) : (

                                    filteredDocuments.map((doc) => (

                                        <div
                                            className="document-row"
                                            key={doc.name}
                                        >

                                            <div className="file-icon">
                                                <FaFileAlt />
                                            </div>

                                            <div className="file-info">

                                                <strong>
                                                    {doc.name}
                                                </strong>

                                                <span>
                                                    {doc.type} • {doc.size}
                                                </span>

                                            </div>

                                            <span className="upload-date">
                                                {doc.uploaded}
                                            </span>

                                            <span className="indexed">
                                                <FaCheckCircle />
                                                Indexed
                                            </span>

                                        </div>

                                    ))

                                )}

                            </div>

                        </div>


                        {/* RIGHT SIDE */}

                        <div className="right-column">

                            {/* RAG PERFORMANCE */}

                            <div className="dashboard-card performance-card">

                                <div className="card-header">

                                    <div>
                                        <h3>
                                            RAG Performance
                                        </h3>

                                        <p>
                                            AI response quality
                                        </p>
                                    </div>

                                    <FaChartLine />

                                </div>

                                <div className="performance-score">

                                    <div className="score-circle">
                                        <strong>94%</strong>
                                        <span>Confidence</span>
                                    </div>

                                    <div className="performance-info">

                                        <div>
                                            <span>Accuracy</span>
                                            <strong>96%</strong>
                                        </div>

                                        <div>
                                            <span>Relevance</span>
                                            <strong>92%</strong>
                                        </div>

                                        <div>
                                            <span>Responses</span>
                                            <strong>156</strong>
                                        </div>

                                    </div>

                                </div>

                            </div>


                            {/* CONFLICT CARD */}

                            <div className="conflict-card">

                                <div className="conflict-icon">
                                    <FaExclamationTriangle />
                                </div>

                                <div className="conflict-content">

                                    <span>
                                        ATTENTION REQUIRED
                                    </span>

                                    <h3>
                                        4 Document Conflicts
                                    </h3>

                                    <p>
                                        Conflicting information
                                        detected between documents.
                                    </p>

                                    <button
                                        onClick={goToConflicts}
                                    >
                                        Resolve Conflicts
                                        <FaChevronRight />
                                    </button>

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* ================= ACTIVITY ================= */}

                    <div className="dashboard-card activity-card">

                        <div className="card-header">

                            <div>
                                <h3>
                                    Recent System Activity
                                </h3>

                                <p>
                                    Latest actions across your
                                    Enterprise RAG system
                                </p>
                            </div>

                        </div>


                        <div className="activity-grid">

                            <div className="activity-item">

                                <div className="activity-dot purple"></div>

                                <div>
                                    <strong>
                                        Document indexed
                                    </strong>

                                    <span>
                                        ds4-cir-kyc.pdf
                                    </span>

                                    <small>
                                        10 minutes ago
                                    </small>
                                </div>

                            </div>


                            <div className="activity-item">

                                <div className="activity-dot blue"></div>

                                <div>
                                    <strong>
                                        New user registered
                                    </strong>

                                    <span>
                                        User account created
                                    </span>

                                    <small>
                                        35 minutes ago
                                    </small>
                                </div>

                            </div>


                            <div className="activity-item">

                                <div className="activity-dot green"></div>

                                <div>
                                    <strong>
                                        Conversation completed
                                    </strong>

                                    <span>
                                        KYC Guidelines
                                    </span>

                                    <small>
                                        1 hour ago
                                    </small>
                                </div>

                            </div>


                            <div className="activity-item">

                                <div className="activity-dot orange"></div>

                                <div>
                                    <strong>
                                        Document uploaded
                                    </strong>

                                    <span>
                                        AML-Guidelines.docx
                                    </span>

                                    <small>
                                        2 hours ago
                                    </small>
                                </div>

                            </div>

                        </div>

                    </div>


                    {/* ================= QUICK ACTIONS ================= */}

                    <div className="quick-section">

                        <div className="section-title">

                            <div>
                                <h3>
                                    Quick Actions
                                </h3>

                                <p>
                                    Frequently used admin tools
                                </p>
                            </div>

                        </div>


                        <div className="quick-grid">

                            <button className="quick-card">

                                <div className="quick-icon purple">
                                    <FaPlus />
                                </div>

                                <div>
                                    <strong>
                                        Add Document
                                    </strong>

                                    <span>
                                        Add new knowledge to RAG
                                    </span>
                                </div>

                                <FaChevronRight />

                            </button>


                            <button className="quick-card">

                                <div className="quick-icon blue">
                                    <FaUsers />
                                </div>

                                <div>
                                    <strong>
                                        Manage Users
                                    </strong>

                                    <span>
                                        Manage enterprise accounts
                                    </span>
                                </div>

                                <FaChevronRight />

                            </button>


                            <button
                                className="quick-card"
                                onClick={goToConflicts}
                            >

                                <div className="quick-icon orange">
                                    <FaExclamationTriangle />
                                </div>

                                <div>
                                    <strong>
                                        Resolve Conflicts
                                    </strong>

                                    <span>
                                        Review conflicting sources
                                    </span>
                                </div>

                                <FaChevronRight />

                            </button>

                        </div>

                    </div>

                </section>

            </main>

        </div>
    );
}