import { useState } from "react";

function ChatWindow({ conversation }) {

    const [question, setQuestion] = useState("");

    const [messages, setMessages] = useState([]);


    const sendMessage = async () => {

        if (!question.trim()) {
            return;
        }

        const userMessage = {
            type: "user",
            text: question
        };

        setMessages((previous) => [
            ...previous,
            userMessage
        ]);

        const currentQuestion = question;

        setQuestion("");


        try {

            let url;

            if (conversation) {

                url =
                    `http://localhost:9000/api/chat` +
                    `?conversationId=${conversation.id}` +
                    `&question=${encodeURIComponent(
                        currentQuestion
                    )}`;

            } else {

                url =
                    `http://localhost:9000/api/chat/simple` +
                    `?question=${encodeURIComponent(
                        currentQuestion
                    )}`;

            }


            const response = await fetch(url);

            if (!response.ok) {
                throw new Error("Chat request failed");
            }

            const answer =
                await response.text();


            setMessages((previous) => [

                ...previous,

                {
                    type: "assistant",
                    text: answer
                }

            ]);

        } catch (error) {

            console.error(error);

            setMessages((previous) => [

                ...previous,

                {
                    type: "assistant",
                    text: "Sorry, something went wrong."
                }

            ]);

        }

    };


    const handleKeyDown = (e) => {

        if (e.key === "Enter" && !e.shiftKey) {

            e.preventDefault();

            sendMessage();

        }

    };


    return (

        <main className="chat-window">


            {/* ================================= */}
            {/* HEADER */}
            {/* ================================= */}

            <div className="chat-header">

                <div>

                    <h2>
                        {conversation
                            ? conversation.title
                            : "New Chat"
                        }
                    </h2>

                    <span>
                        Enterprise Document Assistant
                    </span>

                </div>

            </div>


            {/* ================================= */}
            {/* MESSAGES */}
            {/* ================================= */}

            <div className="messages">

                {messages.length === 0 ? (

                    <div className="welcome">

                        <h1>
                            How can I help you?
                        </h1>

                        <p>
                            Ask questions about your
                            uploaded enterprise documents.
                        </p>

                    </div>

                ) : (

                    messages.map(
                        (message, index) => (

                            <div
                                key={index}
                                className={
                                    `message ${
                                        message.type
                                    }`
                                }
                            >

                                <div className="message-content">

                                    {message.text}

                                </div>

                            </div>

                        )
                    )

                )}

            </div>


            {/* ================================= */}
            {/* INPUT */}
            {/* ================================= */}

            <div className="chat-input-area">

                <div className="chat-input-box">

                    <button
                        className="attach-btn"
                        title="Upload document"
                    >
                        📎
                    </button>


                    <input
                        type="text"
                        placeholder="Ask anything about your documents..."
                        value={question}
                        onChange={(e) =>
                            setQuestion(
                                e.target.value
                            )
                        }
                        onKeyDown={handleKeyDown}
                    />


                    <button
                        className="send-btn"
                        onClick={sendMessage}
                    >
                        ➤
                    </button>

                </div>

                <p className="input-info">
                    Enterprise RAG Assistant can make mistakes.
                    Verify important information.
                </p>

            </div>

        </main>

    );
}

export default ChatWindow;