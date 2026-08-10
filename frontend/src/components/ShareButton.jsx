import { useState } from "react";
import { FaShareAlt, FaCheck } from "react-icons/fa";

export default function ShareButton({ conversationId }) {

    const [copied, setCopied] = useState(false);
    const [loading, setLoading] = useState(false);

    const shareConversation = async () => {

        if (!conversationId) {
            alert("Please select a conversation first.");
            return;
        }

        try {

            setLoading(true);

            const response = await fetch(
                `http://localhost:9000/api/chat/conversations/${conversationId}/share`,
                {
                    method: "PUT"
                }
            );

            if (!response.ok) {
                throw new Error("Unable to create share link");
            }

            const shareUrl = await response.text();

            await navigator.clipboard.writeText(shareUrl);

            setCopied(true);

            setTimeout(() => {
                setCopied(false);
            }, 2500);

        } catch (error) {

            console.error(error);

            alert("Could not create share link.");

        } finally {

            setLoading(false);

        }
    };

    return (
        <button
            className="share-button"
            onClick={shareConversation}
            disabled={loading}
        >

            {copied ? (
                <>
                    <FaCheck />
                    Link Copied
                </>
            ) : (
                <>
                    <FaShareAlt />
                    {loading ? "Creating..." : "Share"}
                </>
            )}

        </button>
    );
}