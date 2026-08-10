import { useState } from "react";
import {
    FaArrowLeft,
    FaExclamationTriangle,
    FaCheckCircle,
    FaBalanceScale,
    FaFileAlt,
    FaSearch,
    FaChevronDown,
    FaClock,
    FaRobot,
    FaUserShield
} from "react-icons/fa";

import { useNavigate } from "react-router-dom";
import "../styles/ConflictResolution.css";

export default function ConflictResolution() {

    const navigate = useNavigate();

    const [search, setSearch] = useState("");
    const [selectedConflict, setSelectedConflict] = useState(null);

    const [conflicts, setConflicts] = useState([
        {
            id: 1,
            title: "KYC Verification Requirements",
            category: "KYC",
            confidence: 72,
            status: "Pending",
            sourceA: {
                name: "KYC-Guidelines-2024.pdf",
                text: "Customer verification must be completed using officially valid documents."
            },
            sourceB: {
                name: "RBI-KYC-Circular.pdf",
                text: "Customer identification may require additional verification depending on risk level."
            },
            recommendation:
                "The newer RBI circular provides additional risk-based verification requirements."
        },
        {
            id: 2,
            title: "Payment Transaction Limit",
            category: "Payments",
            confidence: 84,
            status: "Pending",
            sourceA: {
                name: "Payment-System.pdf",
                text: "The transaction limit is defined according to the standard payment framework."
            },
            sourceB: {
                name: "RBI-Payment-Circular.pdf",
                text: "Transaction limits may be modified according to updated regulatory requirements."
            },
            recommendation:
                "Use the latest RBI circular as the primary source for transaction limits."
        },
        {
            id: 3,
            title: "Customer Due Diligence",
            category: "AML",
            confidence: 91,
            status: "Resolved",
            sourceA: {
                name: "AML-Guidelines.docx",
                text: "Enhanced due diligence is required for high-risk customers."
            },
            sourceB: {
                name: "AML-Policy.pdf",
                text: "High-risk customers must undergo enhanced due diligence procedures."
            },
            recommendation:
                "Both documents provide consistent guidance. No significant conflict detected."
        },
        {
            id: 4,
            title: "Document Retention Period",
            category: "Compliance",
            confidence: 63,
            status: "Pending",
            sourceA: {
                name: "Compliance-Policy.pdf",
                text: "Documents should be retained according to the organization's retention policy."
            },
            sourceB: {
                name: "RBI-Record-Retention.pdf",
                text: "Regulated entities must retain records for the period specified by applicable regulations."
            },
            recommendation:
                "Regulatory retention requirements should take priority over internal policy."
        }
    ]);

    const filteredConflicts = conflicts.filter((conflict) =>
        `${conflict.title} ${conflict.category} ${conflict.status}`
            .toLowerCase()
            .includes(search.toLowerCase())
    );

    const resolveConflict = (id) => {

        setConflicts((current) =>
            current.map((conflict) =>
                conflict.id === id
                    ? {
                        ...conflict,
                        status: "Resolved"
                    }
                    : conflict
            )
        );

        setSelectedConflict(null);
    };

    const getConfidenceClass = (score) => {

        if (score >= 85) {
            return "confidence-high";
        }

        if (score >= 70) {
            return "confidence-medium";
        }

        return "confidence-low";
    };

    return (
        <div className="conflict-page">

            {/* ================= HEADER ================= */}

            <header className="conflict-header">

                <div className="header-left">

                    <button
                        className="back-button"
                        onClick={() => navigate("/admin")}
                    >
                        <FaArrowLeft />
                    </button>

                    <div>
                        <h1>Conflict Resolution</h1>

                        <p>
                            Review and resolve conflicting information
                            across your knowledge base
                        </p>
                    </div>

                </div>

                <div className="header-user">
                    <FaUserShield />

                    <div>
                        <strong>Administrator</strong>
                        <span>Admin Console</span>
                    </div>
                </div>

            </header>


            {/* ================= CONTENT ================= */}

            <main className="conflict-content">

                {/* ================= SUMMARY ================= */}

                <section className="summary-grid">

                    <div className="summary-card">

                        <div className="summary-icon purple">
                            <FaExclamationTriangle />
                        </div>

                        <div>
                            <span>Total Conflicts</span>
                            <strong>{conflicts.length}</strong>
                            <small>Detected by RAG system</small>
                        </div>

                    </div>


                    <div className="summary-card">

                        <div className="summary-icon orange">
                            <FaClock />
                        </div>

                        <div>
                            <span>Pending Review</span>

                            <strong>
                                {
                                    conflicts.filter(
                                        (item) =>
                                            item.status === "Pending"
                                    ).length
                                }
                            </strong>

                            <small>Requires administrator review</small>
                        </div>

                    </div>


                    <div className="summary-card">

                        <div className="summary-icon green">
                            <FaCheckCircle />
                        </div>

                        <div>
                            <span>Resolved</span>

                            <strong>
                                {
                                    conflicts.filter(
                                        (item) =>
                                            item.status === "Resolved"
                                    ).length
                                }
                            </strong>

                            <small>Successfully resolved</small>
                        </div>

                    </div>


                    <div className="summary-card">

                        <div className="summary-icon blue">
                            <FaRobot />
                        </div>

                        <div>
                            <span>AI Confidence</span>

                            <strong>82%</strong>

                            <small>Average confidence score</small>
                        </div>

                    </div>

                </section>


                {/* ================= TOOLBAR ================= */}

                <section className="conflict-toolbar">

                    <div>

                        <h2>Detected Conflicts</h2>

                        <p>
                            Compare documents and select the most
                            reliable source.
                        </p>

                    </div>


                    <div className="toolbar-search">

                        <FaSearch />

                        <input
                            type="text"
                            placeholder="Search conflicts..."
                            value={search}
                            onChange={(e) =>
                                setSearch(e.target.value)
                            }
                        />

                    </div>

                </section>


                {/* ================= CONFLICT LIST ================= */}

                <section className="conflict-list">

                    {filteredConflicts.length === 0 ? (

                        <div className="empty-state">

                            <FaCheckCircle />

                            <h3>No conflicts found</h3>

                            <p>
                                Try searching with a different keyword.
                            </p>

                        </div>

                    ) : (

                        filteredConflicts.map((conflict) => (

                            <div
                                className="conflict-card"
                                key={conflict.id}
                            >

                                {/* CARD HEADER */}

                                <div className="conflict-card-header">

                                    <div className="conflict-title">

                                        <div className="warning-icon">
                                            <FaExclamationTriangle />
                                        </div>

                                        <div>

                                            <h3>
                                                {conflict.title}
                                            </h3>

                                            <span>
                                                Conflict ID #
                                                {conflict.id}
                                            </span>

                                        </div>

                                    </div>


                                    <div className="conflict-meta">

                                        <span className="category-badge">
                                            {conflict.category}
                                        </span>

                                        <span
                                            className={
                                                conflict.status === "Resolved"
                                                    ? "status-badge resolved"
                                                    : "status-badge pending"
                                            }
                                        >
                                            {conflict.status}
                                        </span>

                                    </div>

                                </div>


                                {/* SOURCES */}

                                <div className="source-grid">

                                    {/* SOURCE A */}

                                    <div className="source-card">

                                        <div className="source-header">

                                            <div className="source-file">

                                                <div className="file-icon">
                                                    <FaFileAlt />
                                                </div>

                                                <div>

                                                    <strong>
                                                        Source A
                                                    </strong>

                                                    <span>
                                                        {conflict.sourceA.name}
                                                    </span>

                                                </div>

                                            </div>

                                        </div>

                                        <p>
                                            {conflict.sourceA.text}
                                        </p>

                                    </div>


                                    {/* VS */}

                                    <div className="vs-badge">
                                        <FaBalanceScale />
                                        VS
                                    </div>


                                    {/* SOURCE B */}

                                    <div className="source-card">

                                        <div className="source-header">

                                            <div className="source-file">

                                                <div className="file-icon">
                                                    <FaFileAlt />
                                                </div>

                                                <div>

                                                    <strong>
                                                        Source B
                                                    </strong>

                                                    <span>
                                                        {conflict.sourceB.name}
                                                    </span>

                                                </div>

                                            </div>

                                        </div>

                                        <p>
                                            {conflict.sourceB.text}
                                        </p>

                                    </div>

                                </div>


                                {/* CONFIDENCE */}

                                <div className="confidence-section">

                                    <div className="confidence-heading">

                                        <div>

                                            <strong>
                                                AI Confidence Score
                                            </strong>

                                            <span>
                                                Based on source reliability,
                                                recency and semantic similarity
                                            </span>

                                        </div>

                                        <strong
                                            className={getConfidenceClass(
                                                conflict.confidence
                                            )}
                                        >
                                            {conflict.confidence}%
                                        </strong>

                                    </div>


                                    <div className="confidence-bar">

                                        <div
                                            className={
                                                `confidence-progress ${
                                                    getConfidenceClass(
                                                        conflict.confidence
                                                    )
                                                }`
                                            }
                                            style={{
                                                width:
                                                    `${conflict.confidence}%`
                                            }}
                                        />

                                    </div>

                                </div>


                                {/* RECOMMENDATION */}

                                <div className="recommendation">

                                    <div className="recommendation-icon">
                                        <FaRobot />
                                    </div>

                                    <div>

                                        <strong>
                                            AI Recommendation
                                        </strong>

                                        <p>
                                            {conflict.recommendation}
                                        </p>

                                    </div>

                                </div>


                                {/* ACTIONS */}

                                <div className="conflict-actions">

                                    <button
                                        className="details-button"
                                        onClick={() =>
                                            setSelectedConflict(
                                                conflict
                                            )
                                        }
                                    >
                                        View Details
                                        <FaChevronDown />
                                    </button>


                                    {conflict.status === "Pending" && (

                                        <button
                                            className="resolve-button"
                                            onClick={() =>
                                                resolveConflict(
                                                    conflict.id
                                                )
                                            }
                                        >
                                            <FaCheckCircle />
                                            Resolve Conflict
                                        </button>

                                    )}

                                    {conflict.status === "Resolved" && (

                                        <span className="resolved-label">
                                            <FaCheckCircle />
                                            Resolution Applied
                                        </span>

                                    )}

                                </div>

                            </div>

                        ))

                    )}

                </section>

            </main>


            {/* ================= DETAILS MODAL ================= */}

            {selectedConflict && (

                <div
                    className="modal-overlay"
                    onClick={() =>
                        setSelectedConflict(null)
                    }
                >

                    <div
                        className="details-modal"
                        onClick={(e) =>
                            e.stopPropagation()
                        }
                    >

                        <div className="modal-header">

                            <div>

                                <span>Conflict Details</span>

                                <h2>
                                    {selectedConflict.title}
                                </h2>

                            </div>

                            <button
                                onClick={() =>
                                    setSelectedConflict(null)
                                }
                            >
                                ×
                            </button>

                        </div>


                        <div className="modal-body">

                            <div className="detail-block">

                                <h4>Source A</h4>

                                <strong>
                                    {selectedConflict.sourceA.name}
                                </strong>

                                <p>
                                    {selectedConflict.sourceA.text}
                                </p>

                            </div>


                            <div className="detail-block">

                                <h4>Source B</h4>

                                <strong>
                                    {selectedConflict.sourceB.name}
                                </strong>

                                <p>
                                    {selectedConflict.sourceB.text}
                                </p>

                            </div>


                            <div className="detail-recommendation">

                                <FaRobot />

                                <div>

                                    <strong>
                                        Recommended Resolution
                                    </strong>

                                    <p>
                                        {
                                            selectedConflict.recommendation
                                        }
                                    </p>

                                </div>

                            </div>

                        </div>


                        <div className="modal-footer">

                            <button
                                className="cancel-button"
                                onClick={() =>
                                    setSelectedConflict(null)
                                }
                            >
                                Close
                            </button>

                            {selectedConflict.status === "Pending" && (

                                <button
                                    className="resolve-button"
                                    onClick={() => {
                                        resolveConflict(
                                            selectedConflict.id
                                        );
                                    }}
                                >
                                    <FaCheckCircle />
                                    Resolve Conflict
                                </button>

                            )}

                        </div>

                    </div>

                </div>

            )}

        </div>
    );
}