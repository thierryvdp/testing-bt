<?php
    // Start a session to maintain user-specific data
    session_start();
    // Generate a unique nonce to prevent CSRF attacks
    $_SESSION['nonce'] = substr(str_shuffle(MD5(microtime())), 0, 10);
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Continuous Audio Recording Demo</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    </head>
    <body>
        <!-- Status message area -->
        <p id="status">Recording audio every second...</p>
        <script type="text/javascript">
            // Set the nonce value from the PHP session into the JavaScript context
            window.nonce = "<?php echo $_SESSION['nonce']; ?>";
            let sequenceNumber = 1; // Initialize the sequence number for recordings

            // Function to initialize and manage audio recording
            const recordAudio = () => {
                return new Promise(async resolve => {
                    // Request access to the user's microphone
                    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
                    const mediaRecorder = new MediaRecorder(stream);

                    // Event listener to handle audio data when available
                    mediaRecorder.addEventListener("dataavailable", event => {
                        const audioBlob = event.data; // Blob containing audio data
                        uploadAudio(audioBlob); // Upload the audio blob to the server
                    });

                    // Function to start the recording
                    const start = () => mediaRecorder.start();

                    // Function to stop the recording
                    const stop = () => mediaRecorder.stop();

                    resolve({ start, stop }); // Resolve with start and stop methods
                });
            };

            // Function to upload the recorded audio to the server
            const uploadAudio = audioBlob => {
                // Check if the audio file size exceeds 10 MB
                if (audioBlob.size > (10 * Math.pow(1024, 2))) {
                    document.body.innerHTML += "Too big; could not upload";
                    return;
                }

                // Create a form to send audio data and nonce
                const formData = new FormData();
                formData.append("nonce", window.nonce); // Add the nonce for security
                formData.append("payload", audioBlob); // Add the audio blob

                // Send the form data to the server via POST request
                fetch("save_audio.php", {
                    method: "POST",
                    body: formData
                }).then(response => {
                    if (response.ok) {
                        // Update the status message with the current sequence number
                        document.getElementById("status").innerText = `Partie ${sequenceNumber} faite`;
                        sequenceNumber++; // Increment the sequence number
                    } else {
                        console.error("Failed to save audio."); // Log an error if the upload fails
                    }
                }).catch(error => {
                    console.error("Error uploading audio:", error); // Log any errors during the upload
                });
            };

            // Main function to start recording and manage intervals
            (async () => {
                const recorder = await recordAudio(); // Initialize the recorder
                recorder.start(); // Start the recording

                // Set an interval to stop and restart recording every second
                setInterval(() => {
                    recorder.stop(); // Stop the current recording
                    recorder.start(); // Start a new recording
                }, 1000); // 1000 ms = 1 second
            })();
        </script>
    </body>
</html>
